package org.kaddiya.grorchestrator.guice.factory;

import com.google.inject.name.Named;
import org.kaddiya.grorchestrator.managers.interfaces.DockerRemoteInterface;
import org.kaddiya.grorchestrator.models.core.latest.Host;
import org.kaddiya.grorchestrator.models.core.latest.Instance;

/**
 * Created by Webonise on 24/02/17.
 */
public interface NamedDockerRemoteApiFactory {
    @Named("PullImage")
    DockerRemoteInterface createPuller(Instance instance, Host host);
}

