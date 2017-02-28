package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.guice.factory.DockerContainerActionFactory
import org.kaddiya.grorchestrator.helpers.DockerContainerCreationRequestBuilder
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.DockerRemoteInterface
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.DockerContainerCreationRequest
import org.kaddiya.grorchestrator.models.remotedocker.responses.AbstractDockerInteractionResponse
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse

/**
 * Created by Webonise on 05/07/16.
 */
@CompileStatic
@Log4j
class CreateContainerImpl extends DockerRemoteAPI<DockerContainerCreationResponse> {

    final DockerContainerCreationRequestBuilder containerCreationRequestBuilder;
    final DockerRemoteInterface pullImageImpl

    @Inject
    public CreateContainerImpl(
            @Assisted Instance instance,
            @Assisted Host host, DockerContainerActionFactory actionFactory, DockerContainerCreationRequestBuilder containerCreationRequestBuilder) {
        super(instance, host)
        this.containerCreationRequestBuilder = containerCreationRequestBuilder
        this.pullImageImpl = actionFactory.getImagePuller(instance, host)
        this.pathSegment = "containers/create?name=$instance.name"
    }

    @Override
    Request constructRequest() {

        DockerContainerCreationRequest request = containerCreationRequestBuilder.getContainerCreationRequest(this.instance);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        return new Request.Builder()
                .url(getCanonicalURL(this.pathSegment))
                .post(RequestBody.create(JSON, JsonOutput.toJson(request)))
                .build();
    }

    @Override
    protected Object notFoundHandler() {
        log.info("The image with #$instance.imageName with tag $instance.tag is not found.Going to attempt to pull it")
        pullImageImpl.doWork();
        return (this.doWork() as AbstractDockerInteractionResponse)
    }

    @Override
    AbstractDockerInteractionResponse doWork() {
        return (super.doInternalWork() as AbstractDockerInteractionResponse)

    }
}
