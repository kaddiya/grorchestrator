package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.DockerContainerKillManager;
import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 12/07/16.
 */
public interface DockerContainerKillManagerFactory {

    public DockerContainerKillManager create(Instance instance);
}
