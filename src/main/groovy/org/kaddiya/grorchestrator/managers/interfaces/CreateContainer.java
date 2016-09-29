package org.kaddiya.grorchestrator.managers.interfaces;

import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse;

/**
 * Created by Webonise on 05/07/16.
 */
public interface CreateContainer {

    public DockerContainerCreationResponse createContainer();
}
