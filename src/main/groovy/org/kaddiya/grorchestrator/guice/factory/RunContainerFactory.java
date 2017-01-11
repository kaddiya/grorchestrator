package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.interfaces.RunContainer;
import org.kaddiya.grorchestrator.models.core.previous.Instance;

/**
 * Created by Webonise on 11/07/16.
 */
public interface RunContainerFactory {
    public RunContainer create(Instance instance);
}
