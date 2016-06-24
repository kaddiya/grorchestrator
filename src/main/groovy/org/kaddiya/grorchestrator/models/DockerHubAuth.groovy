package org.kaddiya.grorchestrator.models

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode

/**
 * Created by Webonise on 23/06/16.
 */
@Canonical
@EqualsAndHashCode
class DockerHubAuth {
    String username
    String password
    String auth
    String email
}
