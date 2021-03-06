package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.guice.factory.DockerContainerActionFactory
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.DockerRemoteInterface
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.AbstractDockerInteractionResponse
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericNoContentResponse

/**
 * Created by Webonise on 12/07/16.
 */
@CompileStatic
@Slf4j
class KillContainerImpl extends DockerRemoteAPI<DockerRemoteGenericNoContentResponse> {

    final DockerRemoteInterface containerRemoveManager;

    @Inject
    KillContainerImpl(
            @Assisted Instance instance, @Assisted Host host, DockerContainerActionFactory actionFactory) {
        super(instance, host)
        this.containerRemoveManager = actionFactory.getContainerRemover(instance, host)
        this.pathSegment = "containers/$instance.name/kill"
    }


    @Override
    protected void preHook() {
        log.info("killing the instance with $instance.name")
    }

    @Override
    protected void postHook() {
        log.info("killed the instance with $instance.name")
        containerRemoveManager.doWork()
    }


    @Override
    Request constructRequest() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        return new Request.Builder()
                .url(getCanonicalURL(this.pathSegment))
                .post(RequestBody.create(JSON, "")) //this requires an empty request body
                .build();
    }

    @Override
    AbstractDockerInteractionResponse doWork() {
        return (super.doInternalWork() as AbstractDockerInteractionResponse)
    }
}

