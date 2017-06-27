package org.kaddiya.grorchestrator.helpers;

import org.kaddiya.grorchestrator.models.core.DockerHubAuth;

/**
 * Created by Webonise on 01/03/17.
 */
public interface DockerRegistryAuthFinder {

    public DockerHubAuth getDockerHubAuthForId(String id);
}
