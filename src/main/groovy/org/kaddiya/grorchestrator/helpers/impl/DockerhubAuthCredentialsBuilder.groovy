package org.kaddiya.grorchestrator.helpers.impl

import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.DockerHubAuth

/**
 * Created by Webonise on 29/06/16.
 */
@CompileStatic
class DockerhubAuthCredentialsBuilder {


    String getBase64EncodedValueForCredsWithAuthId(String authId) {
        //this will be replaced with an API call.
        return Base64.encoder.encodeToString(new JsonBuilder(new DockerHubAuth(authId, "proofadmin", "1529.Vienna.1683", "developers@get-proof.com", " ")).toString().bytes)
    }


}

