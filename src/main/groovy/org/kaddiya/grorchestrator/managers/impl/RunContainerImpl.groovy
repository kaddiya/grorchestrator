package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.guice.factory.DockerContainerActionFactory
import org.kaddiya.grorchestrator.helpers.HostConfigBuilder
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.DockerRemoteInterface
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.HostConfig
import org.kaddiya.grorchestrator.models.remotedocker.responses.AbstractDockerInteractionResponse
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericNoContentResponse

/**
 * Created by Webonise on 11/07/16.
 */
@CompileStatic
class RunContainerImpl extends DockerRemoteAPI<DockerRemoteGenericNoContentResponse> {

    final DockerRemoteInterface containerCreatorImpl
    final HostConfigBuilder hostConfigBuilder


    @Inject
    RunContainerImpl(
            @Assisted Instance instance,
            @Assisted Host host, DockerContainerActionFactory actionFactory, HostConfigBuilder hostConfigBuilder) {
        super(instance, host)
        containerCreatorImpl = actionFactory.getContainerCreator(this.instance, this.host)
        this.hostConfigBuilder = hostConfigBuilder
        this.pathSegment = "containers/$instance.name/start"
    }

    @Override
    void preHook() {
        containerCreatorImpl.doWork()
    }

    @Override
    Request constructRequest() {
        String request
        HostConfig config = hostConfigBuilder.constructHostConfig(instance)
        if (this.host.dockerVersion == '1.18') {
            request = new JsonBuilder(config)
        } else {
            request = ""
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url(getCanonicalURL(this.pathSegment))
                .post(RequestBody.create(JSON, request))
                .build();
    }

    @Override
    AbstractDockerInteractionResponse doWork() {
        return (super.doInternalWork() as AbstractDockerInteractionResponse)
    }
}

