package org.kaddiya.grorchestrator.managers.interfaces;

import org.kaddiya.grorchestrator.models.remotedocker.responses.AbstractDockerInteractionResponse;


public interface DockerRemoteInterface<T> {

    public AbstractDockerInteractionResponse doWork();
}
