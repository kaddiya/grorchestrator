package org.kaddiya.grorchestrator.models.remotedocker.requests

import groovy.transform.Canonical

/**
 * Created by Webonise on 05/07/16.
 */
@Canonical
class DockerContainerCreationRequest {
    String image
    Map<String, Object> volumes
    Map<String, Object> exposedPorts
    HostConfig hostConfig


}
