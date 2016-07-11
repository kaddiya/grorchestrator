package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import org.apache.commons.lang.RandomStringUtils
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse

import static groovyx.net.http.ContentType.JSON

/**
 * Created by Webonise on 05/07/16.
 */
@CompileStatic
class DockerContainerCreatorImpl extends DockerRemoteAPI implements DockerContainerCreator {

    @Inject
    public DockerContainerCreatorImpl(@Assisted Instance instance) {
        super(instance)
    }

    @Override
    DockerContainerCreationResponse createContainer() {

        def value = this.instance.imageName + ":" + this.instance.tag

        String charset = (('A'..'Z') + ('0'..'9')).join("")
        Integer length = 9
        String randomContainerName = RandomStringUtils.random(length, charset.toCharArray())

        println("creating a new instance with name $randomContainerName")

        DockerContainerCreationResponse response = this.client.post(
                requestContentType: JSON,
                path: "/containers/create",
                query: [
                        'name': randomContainerName //this has to be changed to a UUID name.After deployment this has to be renamed
                ],
                body: ['Image': value]

        ) as DockerContainerCreationResponse
        response
    }
}
