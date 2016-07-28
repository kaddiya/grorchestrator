package org.kaddiya.grorchestrator.helpers

import com.google.inject.Inject
import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.DockerHubAuth

/**
 * Created by Webonise on 29/06/16.
 */
@CompileStatic
class DockerAuthCredentialsBuilder {

    final EnvironmentVarsResolver environmentVarsResolver

    @Inject
    public DockerAuthCredentialsBuilder(EnvironmentVarsResolver resolver){
        this.environmentVarsResolver = resolver
    }

    DockerHubAuth constructDockerHubAuthenticationCredentials() {

        String registryUsername = environmentVarsResolver.getEnvironmentVarValueForKey("registry_username")
        String registryPassword = environmentVarsResolver.getEnvironmentVarValueForKey("registry_password")
        String registryEmail = environmentVarsResolver.getEnvironmentVarValueForKey("registry_email")
        String registryAuth = environmentVarsResolver.getEnvironmentVarValueForKey("registry_auth")

        return new DockerHubAuth(registryUsername, registryPassword,registryEmail,registryAuth)
    }

    String getbase64EncodedValueForCredentials() {
        return Base64.encoder.encodeToString(new JsonBuilder(constructDockerHubAuthenticationCredentials()).toString().bytes)
    }


}

