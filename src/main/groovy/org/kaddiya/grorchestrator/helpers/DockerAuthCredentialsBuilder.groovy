package org.kaddiya.grorchestrator.helpers

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.DockerHubAuth

/**
 * Created by Webonise on 29/06/16.
 */
@CompileStatic
class DockerAuthCredentialsBuilder {

    final EnvironmentVarsResolver environmentVarsResolver
    private final  DockerHubAuth authObject
    final static String DEFAULT_AUTH_STRING_VALUE = " "

    @Inject
    public DockerAuthCredentialsBuilder(@Assisted DockerHubAuth authObject) {
        this.authObject = authObject;
    }

    DockerHubAuth constructDockerHubAuthenticationCredentials() {

        String registryUsername = authObject.username
        String registryPassword = authObject.password
        String registryEmail = authObject.email
        String registryAuth = DEFAULT_AUTH_STRING_VALUE

        return new DockerHubAuth(registryUsername, registryPassword, registryEmail, registryAuth)
    }

    String getbase64EncodedValueForCredentials() {
        return Base64.encoder.encodeToString(new JsonBuilder(constructDockerHubAuthenticationCredentials()).toString().bytes)
    }


}

