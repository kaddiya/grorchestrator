package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.DockerContainerStarter;
import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 05/07/16.
 */
public interface DockerContainerStarterFactory {

    public DockerContainerStarter create(Instance instance);
}
