package org.kaddiya.grorchestrator.guice.factory

import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 05/07/16.
 */
public interface DockerContainerCreatorFactory {
    public DockerContainerCreator create(Instance instance);
}
