package org.kaddiya.grorchestrator.managers.interfaces

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.managers.interfaces.aggregators.DockerRemoteAPIActionsAggregator
import org.kaddiya.grorchestrator.models.remotedocker.responses.AbstractDockerInteractionResponse


@CompileStatic
public abstract class DockerRemoteAPISequentialAggregator implements DockerRemoteAPIActionsAggregator {

    protected final List<DockerRemoteInterface> actions;

    protected DockerRemoteAPISequentialAggregator(DockerRemoteInterface... actions) {
        this.actions = Arrays.asList(actions);
    }

    @Override
    public List<AbstractDockerInteractionResponse> doWorkAndAggregateResults() {
        return actions.collect { DockerRemoteInterface it ->
            it.doWork()
        }
    }
}