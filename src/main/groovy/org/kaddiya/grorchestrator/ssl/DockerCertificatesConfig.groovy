package org.kaddiya.grorchestrator.ssl

import groovy.transform.Canonical

/**
 * Created by Webonise on 22/07/16.
 */
@Canonical
class DockerCertificatesConfig {

    private File caCertPath;
    private File clientKeyPath;
    private File clientCertPath;

}
