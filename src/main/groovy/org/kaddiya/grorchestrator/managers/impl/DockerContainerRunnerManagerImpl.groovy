package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import groovyx.net.http.HTTPBuilder
import org.kaddiya.grorchestrator.managers.DockerContainerRunnerManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse

import static groovyx.net.http.ContentType.JSON

/**
 * Created by Webonise on 11/07/16.
 */
@CompileStatic
class DockerContainerRunnerManagerImpl extends DockerRemoteAPI implements DockerContainerRunnerManager {

    @Inject
    DockerContainerRunnerManagerImpl(@Assisted Instance instance) {
        super(instance)

    }

    @Override
    void runContainer() {
        println(this.createContainer())
    }

    DockerContainerCreationResponse createContainer(){

        def value = this.instance.imageName + ":" + this.instance.tag

        DockerContainerCreationResponse response = client.post(
                requestContentType: JSON,
                path: "/containers/create",
                query: [
                        'name': "some-name-1" //this has to be changed to a UUID name.After deployment this has to be renamed
                ],
                body: ['Image': value]

        ) as DockerContainerCreationResponse
        println(response.Id)
        response
    }
}
