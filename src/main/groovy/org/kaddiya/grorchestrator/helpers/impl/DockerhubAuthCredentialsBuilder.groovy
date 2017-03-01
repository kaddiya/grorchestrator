package org.kaddiya.grorchestrator.helpers.impl

import com.google.inject.Inject
import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.helpers.DockerRegistryAuthFinder
import org.kaddiya.grorchestrator.models.core.DockerHubAuth

/**
 * Created by Webonise on 29/06/16.
 */
@CompileStatic
class DockerhubAuthCredentialsBuilder {

    @Inject
    DockerRegistryAuthFinder finderImpl

    String getBase64EncodedValueForCredsWithAuthId(String authId) {
        //this will be replaced with an API call.
        DockerHubAuth auth = finderImpl.getDockerHubAuthForId(authId)
        return Base64.encoder.encodeToString(new JsonBuilder(auth).toString().bytes)
    }


}

