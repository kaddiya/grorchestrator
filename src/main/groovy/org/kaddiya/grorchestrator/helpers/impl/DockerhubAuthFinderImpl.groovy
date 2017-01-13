package org.kaddiya.grorchestrator.helpers.impl

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.DockerHubAuth
import org.kaddiya.grorchestrator.models.core.latest.GrorProject
import org.kaddiya.grorchestrator.models.core.latest.Host

/**
 * Created by Webonise on 13/01/17.
 */
@CompileStatic
class DockerhubAuthFinderImpl {

    public DockerHubAuth getAuthObjectFrom(GrorProject project, String authId) {

        DockerHubAuth host = project.authData.find { it ->
            it.key = authId
        }

        if (!host) {
            throw new IllegalStateException("In the current project no authentication configuration with alias $authId is referenced.Are you sure you configured the instances properly?")
        }

        return host

    }
}
