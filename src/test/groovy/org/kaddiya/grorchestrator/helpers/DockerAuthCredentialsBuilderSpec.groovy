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
        System.setProperty("registry.username", "username")
        System.setProperty("registry.password", "password")
        System.setProperty("registry.auth", "")
        System.setProperty("registry.email", "example@email.com")
    }

    def "constructDockerHubAuthenticationCredentials should return a proper DockerHubAuth model"() {
        given:
        builder = new DockerAuthCredentialsBuilder()
        when:
        DockerHubAuth actualAuth = builder.constructDockerHubAuthenticationCredentials()
        DockerHubAuth expectedAuth = new DockerHubAuth("username", "password", "", "example@email.com")
        then:
        assert actualAuth == expectedAuth
    }

    def "getbase64EncodedValueForCredentials should return a proper base64 encoded String"() {
        given:
        builder = new DockerAuthCredentialsBuilder()
        when:
        String result = builder.getbase64EncodedValueForCredentials()
        then:
        assert result == "eyJwYXNzd29yZCI6InBhc3N3b3JkIiwidXNlcm5hbWUiOiJ1c2VybmFtZSIsImVtYWlsIjoiZXhhbXBsZUBlbWFpbC5jb20iLCJhdXRoIjoiIn0="
    }

    def cleanup() {
        System.setProperty("registry.username", "")
        System.setProperty("registry.password", "")
        System.setProperty("registry.auth", "")
        System.setProperty("registry.email", "")
    }
}
