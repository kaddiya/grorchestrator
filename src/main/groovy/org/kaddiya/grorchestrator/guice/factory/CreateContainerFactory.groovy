package org.kaddiya.grorchestrator.guice.factory

import org.kaddiya.grorchestrator.managers.interfaces.CreateContainer
import org.kaddiya.grorchestrator.models.core.DockerHubAuth
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance

/**
 * Created by Webonise on 05/07/16.
 */
public interface CreateContainerFactory {
    public CreateContainer create(Instance instance, Host host, DockerHubAuth authObject);
}
