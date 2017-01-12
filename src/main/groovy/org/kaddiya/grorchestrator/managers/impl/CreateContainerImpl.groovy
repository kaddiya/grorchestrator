package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.guice.factory.PullImageFactory
import org.kaddiya.grorchestrator.helpers.DockerContainerCreationRequestBuilder
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.CreateContainer
import org.kaddiya.grorchestrator.managers.interfaces.PullImage
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.DockerContainerCreationRequest
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse

/**
 * Created by Webonise on 05/07/16.
 */
@CompileStatic
class CreateContainerImpl extends DockerRemoteAPI<DockerContainerCreationResponse> implements CreateContainer {

    final DockerContainerCreationRequestBuilder containerCreationRequestBuilder;
    final PullImage pullImageImpl

    @Inject
    public CreateContainerImpl(
            @Assisted Instance instance, @Assisted Host host,PullImageFactory pullImageFactory, DockerContainerCreationRequestBuilder containerCreationRequestBuilder) {
        super(instance,host)
        this.containerCreationRequestBuilder = containerCreationRequestBuilder
        this.pullImageImpl = pullImageFactory.create(instance,host)
    }

    @Override
    DockerContainerCreationResponse createContainer() {
        return doWork()
    }

    @Override
    Request constructRequest() {
        DockerContainerCreationRequest request = containerCreationRequestBuilder.getContainerCreationRequest(this.instance);
        println("creating a new container for $instance.name with image $request.image")
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url("$baseUrl/containers/create?name=$instance.name")
                .post(RequestBody.create(JSON, JsonOutput.toJson(request)))
                .build();
    }

    @Override
    protected Object notFoundHandler(){
        //if container creation throws a 404 error then it means that we need to pull the iamge
        pullImageImpl.pullImage();
        return createContainer()
    }


}
