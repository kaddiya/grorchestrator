package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.DockerRemoteAPI;
import org.kaddiya.grorchestrator.models.core.latest.Host;
import org.kaddiya.grorchestrator.models.core.latest.Instance;

/**
 * Created by Webonise on 22/07/16.
 */
public interface InspectContainerFactory {

    public DockerRemoteAPI create(Instance instance, Host host);
}

