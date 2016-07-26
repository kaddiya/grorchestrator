package org.kaddiya.grorchestrator.managers.impl

import com.google.gson.Gson
import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.kaddiya.grorchestrator.helpers.DockerContainerCreationRequestBuilder
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.DockerContainerCreationRequest
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse

/**
 * Created by Webonise on 05/07/16.
 */
@CompileStatic
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
        Response result = doWork()
        Gson gson = new Gson();
        DockerContainerCreationResponse obj = gson.fromJson(result.body().charStream(), DockerContainerCreationResponse.class);
        return obj

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
