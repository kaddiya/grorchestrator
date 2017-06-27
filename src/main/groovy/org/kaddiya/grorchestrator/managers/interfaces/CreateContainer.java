package org.kaddiya.grorchestrator.managers.interfaces;

import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse;


public interface CreateContainer {

    public DockerContainerCreationResponse createContainer();
}
