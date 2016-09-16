package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.interfaces.RemoveContainer;
import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 14/07/16.
 */
public interface RemoveContainerFactory {
    RemoveContainer create(Instance instance);
}
