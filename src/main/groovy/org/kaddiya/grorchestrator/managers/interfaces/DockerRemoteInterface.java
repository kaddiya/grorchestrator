package org.kaddiya.grorchestrator.managers.interfaces;

import org.kaddiya.grorchestrator.models.remotedocker.responses.AbstractDockerInteractionResponse;

/**
 * Created by Webonise on 28/02/17.
 */
public interface DockerRemoteInterface<T> {

    public AbstractDockerInteractionResponse doWork();
}
