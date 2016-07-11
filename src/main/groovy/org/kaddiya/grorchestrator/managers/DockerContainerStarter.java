package org.kaddiya.grorchestrator.managers;

import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 05/07/16.
 */
public interface DockerContainerStarter {

    public void startContainer(Instance instance);
}
