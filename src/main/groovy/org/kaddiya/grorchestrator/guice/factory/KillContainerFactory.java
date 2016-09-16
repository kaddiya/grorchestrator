package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.interfaces.KillContainer;
import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 12/07/16.
 */
public interface KillContainerFactory {

    public KillContainer create(Instance instance);
}
