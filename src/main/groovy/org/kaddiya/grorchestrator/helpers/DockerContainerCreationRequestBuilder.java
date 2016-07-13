package org.kaddiya.grorchestrator.helpers;

import org.kaddiya.grorchestrator.models.core.Instance;
import org.kaddiya.grorchestrator.models.remotedocker.requests.DockerContainerCreationRequest;

/**
 * Created by Webonise on 13/07/16.
 */
public interface DockerContainerCreationRequestBuilder {

    public DockerContainerCreationRequest getContainerCreationRequest(Instance instance);
}
