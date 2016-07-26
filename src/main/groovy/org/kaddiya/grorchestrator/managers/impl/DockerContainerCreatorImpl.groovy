package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.json.JsonOutput
import okhttp3.Request
import org.kaddiya.grorchestrator.helpers.DockerContainerCreationRequestBuilder
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.DockerContainerCreationRequest
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse

import static groovyx.net.http.ContentType.JSON

/**
 * Created by Webonise on 05/07/16.
 */
class DockerContainerCreatorImpl extends DockerRemoteAPI implements DockerContainerCreator {

    final DockerContainerCreationRequestBuilder containerCreationRequestBuilder;

    @Inject
    public DockerContainerCreatorImpl(
            @Assisted Instance instance, DockerContainerCreationRequestBuilder containerCreationRequestBuilder) {
        super(instance)
        this.containerCreationRequestBuilder = containerCreationRequestBuilder
    }

    @Override
    DockerContainerCreationResponse createContainer() {


        DockerContainerCreationRequest request = containerCreationRequestBuilder.getContainerCreationRequest(this.instance)
        println("creating a new container for $instance.name with image $request.image")

        println(JsonOutput.toJson(request))
        this.tryCatchClosure {
            DockerContainerCreationResponse response

            /*response = this.client.post(
                    requestContentType: JSON,
                    path: "/containers/create",
                    query: [
                            'name': instance.name
                    ],
                    body: request

            ) as DockerContainerCreationResponse*/
            response
        }
    }

    @Override
    Request constructRequest() {
        return null
    }
}
