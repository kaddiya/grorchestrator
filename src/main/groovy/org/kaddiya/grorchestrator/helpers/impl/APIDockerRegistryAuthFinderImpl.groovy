package org.kaddiya.grorchestrator.helpers.impl

import org.apache.commons.lang.NotImplementedException
import org.kaddiya.grorchestrator.helpers.DockerRegistryAuthFinder
import org.kaddiya.grorchestrator.models.core.DockerHubAuth

/**
 * Created by Webonise on 01/03/17.
 */
class APIDockerRegistryAuthFinderImpl implements DockerRegistryAuthFinder {
    @Override
    DockerHubAuth getDockerHubAuthForId(String id) {
        throw new NotImplementedException()
    }
}
