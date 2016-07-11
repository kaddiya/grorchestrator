package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.factory.DockerContainerCreatorFactory
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerContainerRunnerManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 11/07/16.
 */
@CompileStatic
class DockerContainerRunnerManagerImpl extends DockerRemoteAPI implements DockerContainerRunnerManager {

    final DockerContainerCreator containerCreator


    @Inject
    DockerContainerRunnerManagerImpl(@Assisted Instance instance, DockerContainerCreatorFactory creatorFactory) {
        super(instance)
        containerCreator = creatorFactory.create(this.instance)
    }

    @Override
    void runContainer() {

        //     this.createContainer()

        println(containerCreator.createContainer())
    }

/*    //TODO : this has to be moved to the DockerContainerCreatorImpl class and called.

    DockerContainerCreationResponse createContainer() {
        def value = this.instance.imageName + ":" + this.instance.tag

        String charset = (('A'..'Z') + ('0'..'9')).join("")
        Integer length = 9
        String randomString = RandomStringUtils.random(length, charset.toCharArray())

        println("creating a new instance with name $randomString")
        DockerContainerCreationResponse response = client.post(
                requestContentType: JSON,
                path: "/containers/create",
                query: [
                        'name': randomString
                ],
                body: ['Image': value]

        ) as DockerContainerCreationResponse
        response
    }*/


}

