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

    final static String CURRENT_GROR_VERSION = "0.0.3"

    public static void main(String[] args) {

        Injector grorchestratorInjector = Guice.createInjector(new GrorchestratorModule(
                new DeserialiserModule(), new DockerRemoteAPIModule(), new HelperModule()

        ))

        GrorProjectDeserialiser deserialiser = grorchestratorInjector.getInstance(GrorProjectDeserialiser)


        File grorFile = new File(System.getProperty("user.dir")).listFiles().find { File it ->
            it.name.equals(DEFAULT_GROR_FILE_NAME)
        }
        assert grorFile: "$DEFAULT_GROR_FILE_NAME file not found"
        GrorProject project = deserialiser.constructGrorProject(grorFile)
        assert project: "project cant be constructed"


        if(SupportedSystemActions.values().any{SupportedSystemActions it -> it.name().equalsIgnoreCase(args[0])}){
            GrorProjectSerialiser serialiser = grorchestratorInjector.getInstance(GrorProjectSerialiser)
            PreviousToLatestSchemaUpdator updator = grorchestratorInjector.getInstance(PreviousToLatestSchemaUpdator)
            String action = args[0]
            switch (action.toUpperCase()) {
                case SupportedSystemActions.GROR_UPDATE.name():
                    org.kaddiya.grorchestrator.models.core.latest.GrorProject newProject = updator.updateFromPreviousProject(project)
                    serialiser.serialiseProjectToFile(newProject, "v2_gror.json")
                    println("Done with updating to the new version")
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported Actions")
                    break
            }
        }
        else if(SupportedContainerActions.values().any{SupportedContainerActions it -> it.name().equalsIgnoreCase(args[0])}){

            if(!project.getSystemInfo().getGrorVersion().equals(CURRENT_GROR_VERSION)){
                throw new IllegalStateException("Gror versions dont match.Kindly run the command gror update to generate the new file for your configuration")
            }

            if(args.size() < 2 ){
                throw new IllegalStateException("Please mention the instance to deal with")
            }
            String action = args[0]
            String instanceName = args[1]
            String tag

            if(args.size() > 2){
                tag = args[2]
            }
            else{
                tag = "latest"
            }
            //once the arguments and the environment is prepared load the context
            PullImageFactory dockerImagePullManagerFactory = grorchestratorInjector.getInstance(PullImageFactory)
            RunContainerFactory dockerContainerRunnerFactory = grorchestratorInjector.getInstance(RunContainerFactory)
            KillContainerFactory dockerContainerKillManagerFactory = grorchestratorInjector.getInstance(KillContainerFactory)
            RemoveContainerFactory dockerContainerRemoveManagerFactory = grorchestratorInjector.getInstance(RemoveContainerFactory)
            InspectContainerFactory infoManagerFactory = grorchestratorInjector.getInstance(InspectContainerFactory)
            InstanceFinder instanceFinderImpl = grorchestratorInjector.getInstance(InstanceFinder)

            Instance requestedInstance = instanceFinderImpl.getInstanceToInteractWith(project, instanceName)
            requestedInstance.tag = tag

            //conditionally create the remote api managers if the action has something to do with container actions
            PullImage pullManager = dockerImagePullManagerFactory.create(requestedInstance)
            RunContainer dockerContainerRunnerManager = dockerContainerRunnerFactory.create(requestedInstance)
            KillContainer dockerContainerKillManager = dockerContainerKillManagerFactory.create(requestedInstance)
            RemoveContainer removeManager = dockerContainerRemoveManagerFactory.create(requestedInstance)
            InspectContainer infoManager = infoManagerFactory.create(requestedInstance)

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
                default:
                    throw new IllegalArgumentException("Unsupported Actions")
                    break
            }


        }
        else{
            throw new IllegalArgumentException("Unsupported Actions")
        }


    }

}
