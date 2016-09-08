package org.kaddiya.grorchestrator.models.remotedocker.responses.dockermetadata

import groovy.transform.Canonical

/**
 * Created by Webonise on 08/09/16.
 */
@Canonical
class VersionResponse {
    String Version
    String ApiVersion
    String GitCommit
    String GoVersion
    String Os
    String Arch
    String KernelVersion
}
