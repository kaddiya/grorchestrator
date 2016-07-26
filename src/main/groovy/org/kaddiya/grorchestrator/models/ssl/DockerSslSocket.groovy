package org.kaddiya.grorchestrator.models.ssl

import groovy.transform.Canonical

import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@Canonical
class DockerSslSocket {
    SSLSocketFactory sslSocketFactory
    X509TrustManager trustManager
}