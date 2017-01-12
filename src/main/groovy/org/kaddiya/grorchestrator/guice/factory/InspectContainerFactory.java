package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.interfaces.InspectContainer;
import org.kaddiya.grorchestrator.models.core.latest.Host;
import org.kaddiya.grorchestrator.models.core.latest.Instance;

/**
 * Created by Webonise on 22/07/16.
 */
public interface InspectContainerFactory {

    public InspectContainer create(Instance instance,Host host);
}

