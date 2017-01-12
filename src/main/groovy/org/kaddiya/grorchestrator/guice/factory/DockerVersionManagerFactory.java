package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.interfaces.DockerVersionManager;
import org.kaddiya.grorchestrator.models.core.latest.Host;
import org.kaddiya.grorchestrator.models.core.latest.Instance;

/**
 * Created by Webonise on 08/09/16.
 */
public interface DockerVersionManagerFactory {

    public DockerVersionManager create(Instance instance,Host host);
}
