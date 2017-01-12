package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.guice.factory.RemoveContainerFactory
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.KillContainer
import org.kaddiya.grorchestrator.managers.interfaces.RemoveContainer
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericNoContentResponse

/**
 * Created by Webonise on 12/07/16.
 */
@CompileStatic
class KillContainerImpl extends DockerRemoteAPI<DockerRemoteGenericNoContentResponse> implements KillContainer {

    @Inject
    RemoveContainerFactory containerRemoveMangerFactory

    final RemoveContainer containerRemoveManager;

    @Inject
    KillContainerImpl(
            @Assisted Instance instance, @Assisted Host host, RemoveContainerFactory containerRemoveMangerFactory) {
        super(instance, host)
        this.containerRemoveManager = containerRemoveMangerFactory.create(instance, host)
    }

    @Override
    void killContainer() {
        println("going to kill the container $instance.name")
        DockerRemoteGenericNoContentResponse response = doWork()
        println(response.toString())
        containerRemoveManager.removeContainer()
    }

    @Override
    Request constructRequest() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url("$baseUrl/containers/$instance.name/kill")
                .post(RequestBody.create(JSON, "")) //this requires an empty request body
                .build();
    }

}

