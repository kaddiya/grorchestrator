package org.kaddiya.grorchestrator.managers.interfaces;

import org.kaddiya.grorchestrator.models.remotedocker.responses.dockermetadata.VersionResponse;

public interface DockerVersionManager {
    VersionResponse getDockerVersion();
}
