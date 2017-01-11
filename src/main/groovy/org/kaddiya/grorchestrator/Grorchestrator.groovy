package org.kaddiya.grorchestrator

import com.google.inject.Guice
import com.google.inject.Injector
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.DockerRemoteAPIModule
import org.kaddiya.grorchestrator.guice.GrorchestratorModule
import org.kaddiya.grorchestrator.guice.HelperModule
import org.kaddiya.grorchestrator.guice.DeserialiserModule
import org.kaddiya.grorchestrator.guice.factory.*
import org.kaddiya.grorchestrator.helpers.InstanceFinder
import org.kaddiya.grorchestrator.managers.interfaces.*
import org.kaddiya.grorchestrator.models.core.SupportedSystemActions
import org.kaddiya.grorchestrator.models.core.previous.GrorProject
import org.kaddiya.grorchestrator.models.core.previous.Instance
import org.kaddiya.grorchestrator.models.core.SupportedContainerActions
import org.kaddiya.grorchestrator.deserialisers.GrorProjectDeserialiser
import org.kaddiya.grorchestrator.serialiser.GrorProjectSerialiser
import org.kaddiya.grorchestrator.updators.PreviousToLatestSchemaUpdator

@CompileStatic
class Grorchestrator {

    final static String DEFAULT_GROR_FILE_NAME = "gror.json"

    public static void main(String[] args) {

        Injector grorchestratorInjector = Guice.createInjector(new GrorchestratorModule(
                new DeserialiserModule(), new DockerRemoteAPIModule(), new HelperModule()

        ))

        GrorProjectDeserialiser deserialiser = grorchestratorInjector.getInstance(GrorProjectDeserialiser)
        GrorProjectSerialiser serialiser = grorchestratorInjector.getInstance(GrorProjectSerialiser)
        PreviousToLatestSchemaUpdator updator = grorchestratorInjector.getInstance(PreviousToLatestSchemaUpdator)

        PullImageFactory dockerImagePullManagerFactory = grorchestratorInjector.getInstance(PullImageFactory)
        RunContainerFactory dockerContainerRunnerFactory = grorchestratorInjector.getInstance(RunContainerFactory)
        KillContainerFactory dockerContainerKillManagerFactory = grorchestratorInjector.getInstance(KillContainerFactory)
        RemoveContainerFactory dockerContainerRemoveManagerFactory = grorchestratorInjector.getInstance(RemoveContainerFactory)
        InspectContainerFactory infoManagerFactory = grorchestratorInjector.getInstance(InspectContainerFactory)
        InstanceFinder instanceFinderImpl = grorchestratorInjector.getInstance(InstanceFinder)



        File grorFile = new File(System.getProperty("user.dir")).listFiles().find { File it ->
            it.name.equals(DEFAULT_GROR_FILE_NAME)
        }

        assert grorFile: "$DEFAULT_GROR_FILE_NAME file not found"
        GrorProject project = deserialiser.constructGrorProject(grorFile)
        assert project: "project cant be constructed"

        String tag
        String action
        String instanceName

        Instance requestedInstance

        PullImage pullManager
        RunContainer dockerContainerRunnerManager
        KillContainer dockerContainerKillManager
        RemoveContainer removeManager
        InspectContainer infoManager

        assert args.size() >= 1: "incorrect number of arguments."

        if(SupportedSystemActions.values().any{SupportedSystemActions it -> it.name().equals(args[0])}){
            //the action to be performed is a system actions
            //continue
            action = args[0]
        }
        else if(SupportedContainerActions.values().contains(args[0])){

            if(args.size() < 2 ){
                throw new IllegalStateException("Please mention the instance to deal with")
            }
            action = args[0]
            instanceName = args[1]
            if(args.size() > 2){
                tag = args[2]
            }
            else{
                tag = "latest"
            }
            requestedInstance = instanceFinderImpl.getInstanceToInteractWith(project, instanceName)
            requestedInstance.tag = tag

             pullManager = dockerImagePullManagerFactory.create(requestedInstance)
             dockerContainerRunnerManager = dockerContainerRunnerFactory.create(requestedInstance)
             dockerContainerKillManager = dockerContainerKillManagerFactory.create(requestedInstance)
             removeManager = dockerContainerRemoveManagerFactory.create(requestedInstance)
             infoManager = infoManagerFactory.create(requestedInstance)

        }




        switch (action.toUpperCase()) {
            case SupportedContainerActions.PULL.name():
                pullManager.pullImage()
                println("finished pulling the image for $requestedInstance.imageName:$requestedInstance.tag ")
                break
            case SupportedContainerActions.RUN.name():
                dockerContainerRunnerManager.runContainer();
                println("finished running the container $requestedInstance.imageName:$requestedInstance.tag ")
                break
            case SupportedContainerActions.KILL.name():
                dockerContainerKillManager.killContainer();
                println("finished killing the container $requestedInstance.imageName:$requestedInstance.tag ")
                break
            case SupportedContainerActions.STATUS.name():
                String result = infoManager.getInfo();
                println(result)
                break
            case SupportedContainerActions.REMOVE.name():
                removeManager.removeContainer()
                println("finished removing the container $requestedInstance.imageName:$requestedInstance.tag ")
                break
            case SupportedSystemActions.GROR_UPDATE.name():
                org.kaddiya.grorchestrator.models.core.latest.GrorProject newProject  = updator.updateFromPreviousProject(project)
                serialiser.serialiseProjectToFile(newProject,"v2_gror.json")
                println("Done with updating to the new version")
                break;
            default:
                throw new IllegalArgumentException("Unsupported Actions")
                break
        }


    }

}
