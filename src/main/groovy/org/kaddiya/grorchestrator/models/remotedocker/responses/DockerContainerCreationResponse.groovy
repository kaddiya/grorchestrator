package org.kaddiya.grorchestrator.models.remotedocker.responses

import groovy.transform.Canonical

/**
 * Created by Webonise on 05/07/16.
 */
@Canonical
//unfortunately Docker APIs have word case conventions so i need to have the same:(
class DockerContainerCreationResponse {
    String Id
    String Warnings
}

