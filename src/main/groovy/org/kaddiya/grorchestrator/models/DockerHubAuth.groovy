package org.kaddiya.grorchestrator.models

import groovy.transform.Canonical

/**
 * Created by Webonise on 23/06/16.
 */
@Canonical
class DockerHubAuth {
    String username
    String password
    String auth
    String email
}
