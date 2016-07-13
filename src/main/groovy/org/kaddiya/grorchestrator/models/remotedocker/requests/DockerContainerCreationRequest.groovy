package org.kaddiya.grorchestrator.models.remotedocker.requests

import groovy.transform.Canonical

/**
 * Created by Webonise on 05/07/16.
 */
@Canonical
class DockerContainerCreationRequest {
    String Image
    List<String>Binds
    List<String>Links
    Map<String,Object>Volumes
    Map<String,Object>ExposedPorts

}
