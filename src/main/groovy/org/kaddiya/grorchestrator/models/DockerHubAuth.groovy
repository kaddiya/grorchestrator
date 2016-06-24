package org.kaddiya.grorchestrator.models

import groovy.transform.Canonical
import groovy.transform.Immutable

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
