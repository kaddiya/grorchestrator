package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.factory.DockerContainerCreatorFactory
import org.kaddiya.grorchestrator.helpers.HostConfigBuilder
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerContainerRunnerManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.HostConfig
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse

import static groovyx.net.http.ContentType.JSON

/**
 * Created by Webonise on 11/07/16.
 */
@CompileStatic
class DockerContainerRunnerManagerImpl extends DockerRemoteAPI implements DockerContainerRunnerManager {

    final DockerContainerCreator containerCreator
    final HostConfigBuilder hostConfigBuilder

    @Inject
    DockerContainerRunnerManagerImpl(
            @Assisted Instance instance, DockerContainerCreatorFactory creatorFactory, HostConfigBuilder hostConfigBuilder) {
        super(instance)
        containerCreator = creatorFactory.create(this.instance)
        this.hostConfigBuilder = hostConfigBuilder
    }

    @Override
    void runContainer() {
        DockerContainerCreationResponse containerCreationResponse = containerCreator.createContainer()
        if (!containerCreationResponse) {
            throw new IllegalStateException("Something has gone wrong in the creating the container")
        }
        String path = "/containers/$containerCreationResponse.Id/start"

        HostConfig config = hostConfigBuilder.constructHostConfig(instance)
        println(JsonOutput.toJson(config))
        tryCatchClosure {
            this.client.post(
                    requestContentType: JSON,
                    path: path,
                    body: config
            )
        }
    }


}

