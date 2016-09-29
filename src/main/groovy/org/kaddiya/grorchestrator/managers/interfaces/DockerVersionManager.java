package org.kaddiya.grorchestrator.managers.interfaces;

import org.kaddiya.grorchestrator.models.remotedocker.responses.dockermetadata.VersionResponse;

/**
 * Created by Webonise on 08/09/16.
 */
public interface DockerVersionManager {
    VersionResponse getDockerVersion();
}
