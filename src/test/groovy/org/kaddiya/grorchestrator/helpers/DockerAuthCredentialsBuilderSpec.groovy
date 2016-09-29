package org.kaddiya.grorchestrator.helpers

import org.kaddiya.grorchestrator.models.core.DockerHubAuth
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 29/06/16.
 */
class DockerAuthCredentialsBuilderSpec extends Specification {

    @Shared
    DockerAuthCredentialsBuilder builder

    def setup() {
        System.setProperty("registry_username", "username")
        System.setProperty("registry_password", "password")
        System.setProperty("registry_auth", "")
        System.setProperty("registry_email", "example@email.com")
    }

    def "constructDockerHubAuthenticationCredentials should return a proper DockerHubAuth model"() {
        given:
        builder = new DockerAuthCredentialsBuilder(new EnvironmentVarsResolver())
        when:
        DockerHubAuth actualAuth = builder.constructDockerHubAuthenticationCredentials()
        DockerHubAuth expectedAuth = new DockerHubAuth("username", "password", "example@email.com", "")
        then:
        assert actualAuth == expectedAuth
    }

    def "getbase64EncodedValueForCredentials should return a proper base64 encoded String"() {
        given:
        builder = new DockerAuthCredentialsBuilder(new EnvironmentVarsResolver())
        when:
        String result = builder.getbase64EncodedValueForCredentials()
        then:
        assert result == "eyJwYXNzd29yZCI6InBhc3N3b3JkIiwidXNlcm5hbWUiOiJ1c2VybmFtZSIsImF1dGgiOiIiLCJlbWFpbCI6ImV4YW1wbGVAZW1haWwuY29tIn0="
    }

    def cleanup() {
        System.setProperty("registry_username", "")
        System.setProperty("registry_password", "")
        System.setProperty("registry_auth", "")
        System.setProperty("registry_email", "")
    }
}
