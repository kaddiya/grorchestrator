package org.kaddiya.grorchestrator

import com.google.inject.Guice
import com.google.inject.Injector
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.DockerRemoteAPIModule
import org.kaddiya.grorchestrator.guice.GrorchestratorModule
import org.kaddiya.grorchestrator.guice.SerialiserModule
import org.kaddiya.grorchestrator.guice.factory.DockerImagePullManagerFactory
import org.kaddiya.grorchestrator.managers.DockerImagePullManager
import org.kaddiya.grorchestrator.managers.impl.DockerImagePullManagerImpl
import org.kaddiya.grorchestrator.models.core.Component
import org.kaddiya.grorchestrator.models.core.GrorProject
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.serialisers.GrorProjectSerialiser

import javax.inject.Inject

@CompileStatic
class Grorchestrator {

    final static String DEFAULT_GROR_FILE_NAME = "gror.json"
    
    public static void main(String[] args) {

        Injector grorchestratorInjector = Guice.createInjector(new GrorchestratorModule(
                new SerialiserModule(),new DockerRemoteAPIModule()
        ))
        GrorProjectSerialiser serialiser = grorchestratorInjector.getInstance(GrorProjectSerialiser)

        DockerImagePullManagerFactory factory = grorchestratorInjector.getInstance(DockerImagePullManagerFactory)

        String tag = "latest"

        assert args.size() >= 2 : "incorrect number of arguments."

        String action = args[0]
        String instance = args[1]

        //if the tag is passed then update or set it to latest
        if(args.size() > 2){
            tag = args[2]
        }


        File grorFile = new File(System.getProperty("user.dir")).listFiles().find{File it ->
            it.name.equals(DEFAULT_GROR_FILE_NAME)
        }

        assert grorFile : "$DEFAULT_GROR_FILE_NAME file not found"
        GrorProject project = serialiser.constructGrorProject(grorFile)
        assert project : "project cant be constructed"


        List<Instance> requestedInstance = project.components.collectNested {Component it ->
            it.instances.find { Instance inst ->
                inst.name == instance
            }
        }.grep({it!=null})


        assert  requestedInstance.size() == 1 : "Non Singular value for instance passed"

        Instance instanceToPull = requestedInstance[0]

        DockerImagePullManager pullManager =  factory.create(instanceToPull)

        pullManager.pullImage(instanceToPull.imageName,tag)

        println("finished pulling the images")


    }

}
