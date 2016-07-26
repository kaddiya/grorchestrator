package org.kaddiya.grorchestrator.ssl

import org.kaddiya.grorchestrator.models.ssl.DockerSslSocket

import javax.net.ssl.*
import java.security.KeyStore
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

import static javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm


class SslSocketConfigFactory {

    DockerSslSocket createDockerSslSocket(String certPath) {
        def keyStore = createKeyStore(certPath)
        KeyManagerFactory keyManagerFactory = initKeyManagerFactory(keyStore)
        /*Uncomment this code to get a real trust manager that will verify only the subjectAltNames
        TrustManagerFactory tmf = initTrustManagerFactory(keyStore)
        X509TrustManager realTrustManager = getUniqueX509TrustManager(tmf)*/
        X509TrustManager unsafeTrustManager = getAllowAllHostNameVerifierTrustManager()
        SSLContext sslContext = initSslContext(keyManagerFactory, unsafeTrustManager)
        return new DockerSslSocket(sslSocketFactory: sslContext.socketFactory, trustManager: unsafeTrustManager)
    }


    private SSLContext initSslContext(KeyManagerFactory keyManagerFactory, X509TrustManager trustManager) {
        def sslContext = SSLContext.getInstance("TLS")
        sslContext.init(keyManagerFactory.keyManagers, [trustManager] as TrustManager[], null)
        sslContext
    }

    private X509TrustManager getUniqueX509TrustManager(TrustManagerFactory trustManagerFactory) {
        def trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers: ${trustManagers}")
        }
        (X509TrustManager) trustManagers[0]
    }

    private TrustManagerFactory initTrustManagerFactory(KeyStore keyStore) {
        def trustManagerFactory = TrustManagerFactory.getInstance(getDefaultAlgorithm() as String)
        trustManagerFactory.init(keyStore)
        trustManagerFactory
    }

    private KeyManagerFactory initKeyManagerFactory(KeyStore keyStore) {
        def keyManagerFactory = KeyManagerFactory.getInstance(getDefaultAlgorithm() as String)
        keyManagerFactory.init(keyStore, KeyStoreUtil.KEY_STORE_PASSWORD as char[])
        keyManagerFactory
    }

    //This method has to be removed once we figure out once we fiugure out how to have the certificates adhere to the subjetAltNames
    private X509TrustManager getAllowAllHostNameVerifierTrustManager() {
        X509TrustManager tm = new X509TrustManager() {
            @Override
            void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0]
            }
        }
        return tm
    }

    private KeyStore createKeyStore(dockerCertPath) {
        KeyStoreUtil.createDockerKeyStore(new File(dockerCertPath as String).absolutePath)
    }
}