package org.kaddiya.grorchestrator

import com.google.inject.Guice
import com.google.inject.Injector
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.DockerRemoteAPIModule
import org.kaddiya.grorchestrator.guice.GrorchestratorModule
import org.kaddiya.grorchestrator.guice.HelperModule
import org.kaddiya.grorchestrator.guice.SerialiserModule
import org.kaddiya.grorchestrator.guice.factory.DockerContainerCreatorFactory
import org.kaddiya.grorchestrator.guice.factory.DockerContainerRunnerFactory
import org.kaddiya.grorchestrator.guice.factory.DockerImagePullManagerFactory
import org.kaddiya.grorchestrator.helpers.InstanceFinder
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerContainerRunnerManager
import org.kaddiya.grorchestrator.managers.DockerImagePullManager
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

        DockerImagePullManagerFactory dockerImagePullManagerFactory = grorchestratorInjector.getInstance(DockerImagePullManagerFactory)
        DockerContainerCreatorFactory dockerContainerCreatorFactory = grorchestratorInjector.getInstance(DockerContainerCreatorFactory)
        DockerContainerRunnerFactory dockerContainerRunnerFactory = grorchestratorInjector.getInstance(DockerContainerRunnerFactory)
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
        if(tag){
            requestedInstance.tag = tag
        }
        else{
            requestedInstance.tag = "latest"
        }

        DockerImagePullManager pullManager = dockerImagePullManagerFactory.create(requestedInstance)
        DockerContainerCreator dockerContainerCreator = dockerContainerCreatorFactory.create(requestedInstance)
        DockerContainerRunnerManager dockerContainerRunnerManager = dockerContainerRunnerFactory.create(requestedInstance)
        assert dockerContainerCreator

        switch (action) {
            case SupportedActions.PULL_IMAGE.name():
                pullManager.pullImage()
                println("finished pulling the images")
                break
            case SupportedActions.CREATE_CONTAINER.name():
                dockerContainerCreator.createContainer()
                println("finished creating the container")
                break
            case SupportedActions.RUN_CONTAINER.name():
                dockerContainerRunnerManager.runContainer();
                println("finished running the container")
                break
            default:
                throw new IllegalArgumentException("Unsupported Actions")
                break
        }


    }

}
