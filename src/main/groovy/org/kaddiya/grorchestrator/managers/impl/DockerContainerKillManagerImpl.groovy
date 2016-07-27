package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.guice.factory.DockerContainerRemoveMangerFactory
import org.kaddiya.grorchestrator.managers.DockerContainerKillManager
import org.kaddiya.grorchestrator.managers.DockerContainerRemoveManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerKillContainerResponse

/**
 * Created by Webonise on 12/07/16.
 */
@CompileStatic
class DockerContainerKillManagerImpl extends DockerRemoteAPI<DockerContainerKillContainerResponse> implements DockerContainerKillManager {

    @Inject
    DockerContainerRemoveMangerFactory containerRemoveMangerFactory

    final DockerContainerRemoveManager containerRemoveManager;

    @Inject
    DockerContainerKillManagerImpl(
            @Assisted Instance instance, DockerContainerRemoveMangerFactory containerRemoveMangerFactory) {
        super(instance)
        this.containerRemoveManager = containerRemoveMangerFactory.create(instance)
    }

    @Override
    void killContainer() {
        println("going to kill the container $instance.name")
        doWork()
        println("finished killing the container.Now going to remove the name $instance.name")
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

