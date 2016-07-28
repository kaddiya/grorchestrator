package org.kaddiya.grorchestrator.models.ssl

import groovy.transform.Canonical

/**
 * Created by Webonise on 22/07/16.
 */
@Canonical
class DockerSSLAuthenticationConfig {

    private File caCertPath;
    private File clientKeyPath;
    private File clientCertPath;

}
