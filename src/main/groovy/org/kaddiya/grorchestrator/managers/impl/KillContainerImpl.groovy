package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.guice.factory.RemoveContainerFactory
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.DockerRemoteInterface
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
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
            @Assisted Instance instance, @Assisted Host host, RemoveContainerFactory containerRemoveMangerFactory) {
        super(instance, host)
        this.containerRemoveManager = containerRemoveMangerFactory.create(instance, host)
        this.pathSegment = "containers/$instance.name/kill"
    }


    @Override
    protected void preHook() {
        log.info("going to kill the container $instance.name")
    }

    @Override
    protected void postHook() {
        log.info("going to remove the container $instance.name")
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
    DockerRemoteGenericNoContentResponse doWork() {
        return (super.doInternalWork() as DockerRemoteGenericNoContentResponse)
    }
}

