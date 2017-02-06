package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.guice.factory.CreateContainerFactory
import org.kaddiya.grorchestrator.helpers.HostConfigBuilder
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.CreateContainer
import org.kaddiya.grorchestrator.managers.interfaces.RunContainer
import org.kaddiya.grorchestrator.models.core.DockerHubAuth
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.HostConfig
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericOKResponse

/**
 * Created by Webonise on 11/07/16.
 */
@CompileStatic
class RunContainerImpl extends DockerRemoteAPI<DockerRemoteGenericOKResponse> implements RunContainer {

    final CreateContainer containerCreatorImpl
    final HostConfigBuilder hostConfigBuilder


    @Inject
    RunContainerImpl(
            @Assisted Instance instance,
            @Assisted Host host,
            @Assisted DockerHubAuth authObject, CreateContainerFactory creatorFactory, HostConfigBuilder hostConfigBuilder) {
        super(instance, host)
        containerCreatorImpl = creatorFactory.create(this.instance, this.host, authObject)
        this.hostConfigBuilder = hostConfigBuilder
    }

    @Override
    void runContainer() {
        DockerContainerCreationResponse containerCreationResponse = containerCreatorImpl.createContainer()
        if (!containerCreationResponse) {
            throw new IllegalStateException("Something has gone wrong in the creating the container")
        }
        doWork()
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
        /*need to call
        this.getCanonicalURL("/containers/$instance.name/start")
        and pass it to URL
        */
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url(getCanonicalURL("/containers/$instance.name/start"))
                .post(RequestBody.create(JSON, request))
                .build();
    }


}

