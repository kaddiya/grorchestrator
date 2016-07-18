package org.kaddiya.grorchestrator.helpers

import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.DockerHubAuth

/**
 * Created by Webonise on 29/06/16.
 */
@CompileStatic
class DockerAuthCredentialsBuilder {

    DockerHubAuth constructDockerHubAuthenticationCredentials() {
        assert System.getProperty("registry_username") : "Did not find value set for the registry username"
        assert System.getProperty("registry_password") : "Did not find value set for the registry password"
        assert System.getProperty("registry_email")    : "Did not find value set for the registry email"
        assert System.getProperty("registry_auth") != null

        return new DockerHubAuth(System.getProperty("registry_username"), System.getProperty("registry_password"), System.getProperty("registry_auth"), System.getProperty("registry_email"))
    }

    String getbase64EncodedValueForCredentials() {
        return Base64.encoder.encodeToString(new JsonBuilder(constructDockerHubAuthenticationCredentials()).toString().bytes)
    }


}

