package org.kaddiya.grorchestrator

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.managers.DockerImagePullManager
import org.kaddiya.grorchestrator.managers.impl.DockerImagePullManagerImpl
import org.kaddiya.grorchestrator.models.core.Component
import org.kaddiya.grorchestrator.models.core.GrorProject
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.serialisers.GrorProjectSerialiser
import org.kaddiya.grorchestrator.serialisers.GrorProjectSerialiserImpl

@CompileStatic
class Grorchestrator {

    final static GrorProjectSerialiser serialiser = new GrorProjectSerialiserImpl()
    final static String DEFAULT_GROR_FILE_NAME = "gror.json"

    public static void main(String[] args) {
        assert args.size() == 3 : "incorrect number of arguments."

        String action = args[0]
        String instance = args[1]
        String tag = args[2]

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

        DockerImagePullManager pullManager = new DockerImagePullManagerImpl(instanceToPull);

        pullManager.pullImage(instanceToPull.imageName,tag)

        println("finished pulling the images")


    }

}
