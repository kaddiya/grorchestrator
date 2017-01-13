package org.kaddiya.grorchestrator.models.core

import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Created by Webonise on 23/06/16.
 */
@Canonical
@ToString(excludes = "key")
class DockerHubAuth {
    String key
    String username
    String password
    String email
    String auth = " "
}
