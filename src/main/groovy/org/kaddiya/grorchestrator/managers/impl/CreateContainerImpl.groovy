package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.helpers.DockerContainerCreationRequestBuilder
import org.kaddiya.grorchestrator.managers.CreateContainer
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.DockerContainerCreationRequest
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse

/**
 * Created by Webonise on 05/07/16.
 */
@CompileStatic
class CreateContainerImpl extends DockerRemoteAPI<DockerContainerCreationResponse> implements CreateContainer {

    final DockerContainerCreationRequestBuilder containerCreationRequestBuilder;

    @Inject
    public CreateContainerImpl(
            @Assisted Instance instance, DockerContainerCreationRequestBuilder containerCreationRequestBuilder) {
        super(instance)
        this.containerCreationRequestBuilder = containerCreationRequestBuilder
    }

    @Override
    DockerContainerCreationResponse createContainer() {
        return doWork()
    }

    @Override
    Request constructRequest() {
        DockerContainerCreationRequest request = containerCreationRequestBuilder.getContainerCreationRequest(this.instance)
        println("creating a new container for $instance.name with image $request.image")
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url("$baseUrl/containers/create?name=$instance.name")
                .post(RequestBody.create(JSON, JsonOutput.toJson(request)))
                .build();
    }


}
