package org.kaddiya.grorchestrator

import com.google.inject.Guice
import com.google.inject.Injector
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.deserialisers.latest.GrorProjectDeserialiserImpl
import org.kaddiya.grorchestrator.guice.DeserialiserModule
import org.kaddiya.grorchestrator.guice.DockerRemoteAPIModule
import org.kaddiya.grorchestrator.guice.GrorchestratorModule
import org.kaddiya.grorchestrator.guice.HelperModule
import org.kaddiya.grorchestrator.guice.factory.*
import org.kaddiya.grorchestrator.helpers.InstanceFinder
import org.kaddiya.grorchestrator.helpers.impl.HostFinderImpl
import org.kaddiya.grorchestrator.managers.interfaces.*
import org.kaddiya.grorchestrator.managers.interfaces.monitoringactions.InstancesLister
import org.kaddiya.grorchestrator.models.core.SupportedContainerActions
import org.kaddiya.grorchestrator.models.core.SupportedMonitoringActions
import org.kaddiya.grorchestrator.models.core.SupportedSystemActions
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.core.previous.GrorProject
import org.kaddiya.grorchestrator.models.monitoringactions.InstanceSummary
import org.kaddiya.grorchestrator.serialiser.GrorProjectSerialiser
import org.kaddiya.grorchestrator.updators.PreviousToLatestSchemaUpdator

@CompileStatic
class Grorchestrator {

    final static String DEFAULT_GROR_FILE_NAME = "gror.json"

    final static String CURRENT_GROR_VERSION = "1.0.0"

    final static String ACTUAL_GROR_FILE_NAME = "v" + CURRENT_GROR_VERSION + "_" + DEFAULT_GROR_FILE_NAME

    public static void main(String[] args) {

        Injector grorchestratorInjector = Guice.createInjector(new GrorchestratorModule(
                new DeserialiserModule(), new DockerRemoteAPIModule(), new HelperModule()

        ))

        GrorProjectDeserialiserImpl latestDeserialiser = grorchestratorInjector.getInstance(GrorProjectDeserialiserImpl)
        org.kaddiya.grorchestrator.deserialisers.previous.GrorProjectDeserialiserImpl previousDeserialiser = grorchestratorInjector.getInstance(org.kaddiya.grorchestrator.deserialisers.previous.GrorProjectDeserialiserImpl)

        //attempt to get a handle on the gror file of the current version
        File grorFile = new File(System.getProperty("user.dir")).listFiles().find { File it ->
            it.name.equals(ACTUAL_GROR_FILE_NAME)

        }

        //if the gror file for the current version is not found then try to get a handle on the default one
        if (!grorFile) {
            grorFile = new File(System.getProperty("user.dir")).listFiles().find { File it ->
                it.name.equals(DEFAULT_GROR_FILE_NAME)
            }
            GrorProject project = previousDeserialiser.constructGrorProject(grorFile)
            assert project: "project cant be constructed"

            if (SupportedSystemActions.values().any { SupportedSystemActions it -> it.name().equalsIgnoreCase(args[0]) }) {
                GrorProjectSerialiser serialiser = grorchestratorInjector.getInstance(GrorProjectSerialiser)
                PreviousToLatestSchemaUpdator updator = grorchestratorInjector.getInstance(PreviousToLatestSchemaUpdator)
                String action = args[0]

                switch (action.toUpperCase()) {
                    case SupportedSystemActions.GROR_UPDATE.name():
                        org.kaddiya.grorchestrator.models.core.latest.GrorProject newProject = updator.updateFromPreviousProject(project)
                        serialiser.serialiseProjectToFile(newProject, ACTUAL_GROR_FILE_NAME)
                        println("Done with updating to the new version")
                        System.exit(0)
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported Actions")
                        break
                }
            } else {
                throw new IllegalStateException("You have attemped to run a command with incorrect gror file format.Please run gror_update")
            }
        }
        if (grorFile) {
            org.kaddiya.grorchestrator.models.core.latest.GrorProject project = latestDeserialiser.constructGrorProject(grorFile)
            String action = args[0]
            String tag


            if (args.size() > 2) {
                tag = args[2]
            } else {
                tag = "latest"
            }
            if (SupportedContainerActions.values().any { SupportedContainerActions it -> it.name().equalsIgnoreCase(args[0]) }) {
                String instanceName = args[1]

                if (!project.getSystemInfo().getGrorVersion().equals(CURRENT_GROR_VERSION)) {
                    throw new IllegalStateException("Gror versions dont match.Kindly run the command gror update to generate the new file for your configuration")
                }

                if (args.size() < 2) {
                    throw new IllegalStateException("Please mention the instance to deal with")
                }

                //once the arguments and the environment is prepared load the context
                PullImageFactory dockerImagePullManagerFactory = grorchestratorInjector.getInstance(PullImageFactory)
                RunContainerFactory dockerContainerRunnerFactory = grorchestratorInjector.getInstance(RunContainerFactory)
                KillContainerFactory dockerContainerKillManagerFactory = grorchestratorInjector.getInstance(KillContainerFactory)
                RemoveContainerFactory dockerContainerRemoveManagerFactory = grorchestratorInjector.getInstance(RemoveContainerFactory)
                InspectContainerFactory infoManagerFactory = grorchestratorInjector.getInstance(InspectContainerFactory)
                InstanceFinder instanceFinderImpl = grorchestratorInjector.getInstance(InstanceFinder)
                HostFinderImpl hostFinderImpl = grorchestratorInjector.getInstance(HostFinderImpl)


                Instance requestedInstance = instanceFinderImpl.getInstanceToInteractWith(project, instanceName)

                requestedInstance.tag = tag
                Host requestedHost = hostFinderImpl.getHostToInteractWith(project, requestedInstance.hostId)

                //conditionally create the remote api managers if the action has something to do with container actions
                PullImage pullManager = dockerImagePullManagerFactory.create(requestedInstance, requestedHost)
                RunContainer dockerContainerRunnerManager = dockerContainerRunnerFactory.create(requestedInstance, requestedHost)
                KillContainer dockerContainerKillManager = dockerContainerKillManagerFactory.create(requestedInstance, requestedHost)
                RemoveContainer removeManager = dockerContainerRemoveManagerFactory.create(requestedInstance, requestedHost)
                InspectContainer infoManager = infoManagerFactory.create(requestedInstance, requestedHost)

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
                        throw new IllegalArgumentException("Unsupported Container Action requested")
                        break
                }
            } else if (SupportedMonitoringActions.values().any { SupportedMonitoringActions it -> it.name().equalsIgnoreCase(args[0]) }) {
                InstanceListerFactory instanceListerFactory = grorchestratorInjector.getInstance(InstanceListerFactory)
                InstancesLister instancesListerImpl = instanceListerFactory.create()

                switch (action.toUpperCase()) {
                    case SupportedMonitoringActions.LIST.name():
                        List<InstanceSummary> result = instancesListerImpl.getSummaryOfAllInstances(project)
                        println("Instance Name\t\t\tHostIp\t\t\tImageName \n")
                        result.each { it ->
                            println("$it.instanceName\t\t\t$it.hostIp\t\t\t$it.imageName")
                        }
                        break

                    default:
                        throw new IllegalArgumentException("Unsupported Monitoring Action requested")
                        break
                }

            }
        } else {
            throw new IllegalStateException("YOu dont seem to have a gror.json file in the current folkder")
        }
    }

}
