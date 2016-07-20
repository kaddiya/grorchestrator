package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.DockerContainerRunnerManager;
import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 11/07/16.
 */
public interface DockerContainerRunnerFactory {
    public DockerContainerRunnerManager create(Instance instance);
}
