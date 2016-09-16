package org.kaddiya.grorchestrator

import com.google.inject.Guice
import com.google.inject.Injector
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.DockerRemoteAPIModule
import org.kaddiya.grorchestrator.guice.GrorchestratorModule
import org.kaddiya.grorchestrator.guice.HelperModule
import org.kaddiya.grorchestrator.guice.SerialiserModule
import org.kaddiya.grorchestrator.guice.factory.*
import org.kaddiya.grorchestrator.helpers.InstanceFinder
import org.kaddiya.grorchestrator.managers.*
import org.kaddiya.grorchestrator.models.core.GrorProject
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.SupportedActions
import org.kaddiya.grorchestrator.serialisers.GrorProjectSerialiser

@CompileStatic
class Grorchestrator {

    final static String DEFAULT_GROR_FILE_NAME = "gror.json"

    public static void main(String[] args) {

        Injector grorchestratorInjector = Guice.createInjector(new GrorchestratorModule(
                new SerialiserModule(), new DockerRemoteAPIModule(), new HelperModule()

        ))

        GrorProjectSerialiser serialiser = grorchestratorInjector.getInstance(GrorProjectSerialiser)

        PullImageFactory dockerImagePullManagerFactory = grorchestratorInjector.getInstance(PullImageFactory)
        RunContainerFactory dockerContainerRunnerFactory = grorchestratorInjector.getInstance(RunContainerFactory)
        KillContainerFactory dockerContainerKillManagerFactory = grorchestratorInjector.getInstance(KillContainerFactory)
        RemoveContainerFactory dockerContainerRemoveManagerFactory = grorchestratorInjector.getInstance(RemoveContainerFactory)
        InspectContainerFactory infoManagerFactory = grorchestratorInjector.getInstance(InspectContainerFactory)
        InstanceFinder instanceFinderImpl = grorchestratorInjector.getInstance(InstanceFinder)


        String tag

        assert args.size() >= 2: "incorrect number of arguments."

        String action = args[0]
        String instanceName = args[1]

        //if the tag is passed then update or let it be default
        if (args.size() > 2) {
            tag = args[2]
        }


        File grorFile = new File(System.getProperty("user.dir")).listFiles().find { File it ->
            it.name.equals(DEFAULT_GROR_FILE_NAME)
        }

        assert grorFile: "$DEFAULT_GROR_FILE_NAME file not found"
        GrorProject project = serialiser.constructGrorProject(grorFile)
        assert project: "project cant be constructed"
        Instance requestedInstance = instanceFinderImpl.getInstanceToInteractWith(project, instanceName)
        if (tag) {
            requestedInstance.tag = tag
        } else {
            requestedInstance.tag = "latest"
        }

        PullImage pullManager = dockerImagePullManagerFactory.create(requestedInstance)
        RunContainer dockerContainerRunnerManager = dockerContainerRunnerFactory.create(requestedInstance)
        KillContainer dockerContainerKillManager = dockerContainerKillManagerFactory.create(requestedInstance)
        RemoveContainer removeManager = dockerContainerRemoveManagerFactory.create(requestedInstance)
        InspectContainer infoManager = infoManagerFactory.create(requestedInstance)

        switch (action.toUpperCase()) {
            case SupportedActions.PULL.name():
                pullManager.pullImage()
                println("finished pulling the image for $requestedInstance.imageName:$requestedInstance.tag ")
                break
            case SupportedActions.RUN.name():
                dockerContainerRunnerManager.runContainer();
                println("finished running the container $requestedInstance.imageName:$requestedInstance.tag ")
                println(infoManager.getInfo())
                break
            case SupportedActions.KILL.name():
                dockerContainerKillManager.killContainer();
                println("finished killing the container $requestedInstance.imageName:$requestedInstance.tag ")
                break
            case SupportedActions.STATUS.name():
                String result = infoManager.getInfo();
                println(result)
                break
            case SupportedActions.REMOVE.name():
                removeManager.removeContainer()
                println("finished removing the container $requestedInstance.imageName:$requestedInstance.tag ")
                break
            default:
                throw new IllegalArgumentException("Unsupported Actions")
                break
        }


    }

}
