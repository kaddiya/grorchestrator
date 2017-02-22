package org.kaddiya.grorchestrator.helpers

import org.kaddiya.grorchestrator.helpers.impl.DockerhubAuthCredentialsBuilder
import org.kaddiya.grorchestrator.models.core.DockerHubAuth
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 29/06/16.
 */
class DockerAuthCredentialsBuilderSpec extends Specification {

    @Shared
    DockerhubAuthCredentialsBuilder builder = new DockerhubAuthCredentialsBuilder()


    def "getbase64EncodedValueForCredentials should return a proper base64 encoded String"() {
        given:

        DockerHubAuth acutalAuth = new DockerHubAuth("default-key", "username", "password", "email@example.com", "")
        when:
        String result = builder.getbase64EncodedValueForCredentials(acutalAuth)
        then:
        assert result == "eyJwYXNzd29yZCI6InBhc3N3b3JkIiwia2V5IjoiZGVmYXVsdC1rZXkiLCJ1c2VybmFtZSI6InVzZXJuYW1lIiwiYXV0aCI6IiIsImVtYWlsIjoiZW1haWxAZXhhbXBsZS5jb20ifQ=="
    }


}
