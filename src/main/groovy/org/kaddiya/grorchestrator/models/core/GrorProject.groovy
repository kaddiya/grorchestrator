package org.kaddiya.grorchestrator.models.core

import groovy.transform.Canonical

/**
 * Created by Webonise on 19/03/16.
 */
@Canonical
class GrorProject {
    SystemInfo systemInfo
    DockerHubAuth dockerHubAuthCreds
    List<Component> components
}
