package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.RunContainer;
import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 11/07/16.
 */
public interface RunContainerFactory {
    public RunContainer create(Instance instance);
}
