package org.kaddiya.grorchestrator.guice.factory

import org.kaddiya.grorchestrator.managers.interfaces.CreateContainer
import org.kaddiya.grorchestrator.models.core.previous.Instance

/**
 * Created by Webonise on 05/07/16.
 */
public interface CreateContainerFactory {
    public CreateContainer create(Instance instance);
}
