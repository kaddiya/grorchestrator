package org.kaddiya.grorchestrator.helpers;

import org.kaddiya.grorchestrator.models.core.Instance;
import org.kaddiya.grorchestrator.models.remotedocker.requests.DockerContainerCreationRequest;
import org.kaddiya.grorchestrator.models.remotedocker.requests.HostConfig;

import java.util.Map;

/**
 * Created by Webonise on 13/07/16.
 */
public interface DockerContainerCreationRequestBuilder {

    public DockerContainerCreationRequest getContainerCreationRequest(Instance instance);

    public Map<String,Object> getPortMappingsFromInstance(Instance instance);

    public Map<String,Object> getMountBindings(Instance instance);

    HostConfig getHostConfig(Instance p);
}
