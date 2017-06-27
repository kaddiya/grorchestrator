package org.kaddiya.grorchestrator.managers.impl

import groovy.transform.CompileStatic
import okhttp3.Request
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.DockerRemoteInterface
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.AbstractDockerInteractionResponse

@CompileStatic
class RedeployContainerImpl extends DockerRemoteAPI<AbstractDockerInteractionResponse> {

    final DockerRemoteInterface containerRemoveManager;
    final DockerRemoteInterface containerRunnerManager;

    RedeployContainerImpl(Instance instance, Host host) {
        super(instance, host)
    }

    @Override
    protected void preHook() {

    }

    @Override
    protected void postHook() {

    }

    @Override
    protected Request constructRequest() {
        return null
    }

    @Override
    AbstractDockerInteractionResponse doWork() {
        return null
    }
}
