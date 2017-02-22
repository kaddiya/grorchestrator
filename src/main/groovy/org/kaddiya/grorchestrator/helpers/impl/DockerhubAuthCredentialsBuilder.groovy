package org.kaddiya.grorchestrator.helpers.impl

import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.DockerHubAuth

/**
 * Created by Webonise on 29/06/16.
 */
@CompileStatic
class DockerhubAuthCredentialsBuilder {

    String getbase64EncodedValueForCredentials(DockerHubAuth authObject) {
        return Base64.encoder.encodeToString(new JsonBuilder(authObject).toString().bytes)
    }


}

