package org.kaddiya.grorchestrator.managers.interfaces.aggregators;


import org.kaddiya.grorchestrator.models.remotedocker.responses.AbstractDockerInteractionResponse;

import java.util.List;

public interface DockerRemoteAPIActionsAggregator {

    List<AbstractDockerInteractionResponse> doWorkAndAggregateResults();
}
