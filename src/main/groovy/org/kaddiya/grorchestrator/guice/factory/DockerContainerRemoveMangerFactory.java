package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.DockerContainerRemoveManager;
import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 14/07/16.
 */
public interface DockerContainerRemoveMangerFactory {
    DockerContainerRemoveManager create(Instance instance);
}
