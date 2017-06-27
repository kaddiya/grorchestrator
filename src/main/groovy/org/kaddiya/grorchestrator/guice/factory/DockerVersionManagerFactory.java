package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.DockerRemoteAPI;
import org.kaddiya.grorchestrator.models.core.latest.Host;
import org.kaddiya.grorchestrator.models.core.latest.Instance;

/**
 * Created by Webonise on 08/09/16.
 */
public interface DockerVersionManagerFactory {

    public DockerRemoteAPI create(Instance instance, Host host);
}
