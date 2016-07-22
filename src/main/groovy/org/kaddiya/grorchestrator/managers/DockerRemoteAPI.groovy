package org.kaddiya.grorchestrator.managers

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 24/06/16.
 */
abstract class DockerRemoteAPI {

    final Instance instance;

    final String baseUrl;

    final String apiUrl;

    final HTTPBuilder client;

    final RESTClient restClient;

    final String protocol

    public DockerRemoteAPI(Instance instance) {
        this.protocol = derieveProtocol(instance)
        this.instance = instance
        //construct the baseURL

        this.baseUrl = "$protocol://$instance.host.ip:$instance.host.dockerPort"

        //need to deprecate the HTTPBUILEr
        this.client = new HTTPBuilder(baseUrl)

        SSLSocketFactory sf = new SSLSocketFactory
        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
        this.client.client.connectionManager.schemeRegistry.register(new Scheme("https", sf, 443))
        this.client.client.connectionManager.sslS
        this.restClient = new RESTClient(baseUrl)
    }

    String derieveProtocol(Instance instance) {
        instance.host.protocol ? instance.host.protocol : "http"
    }

    def tryCatchClosure(Closure closure) {
        try {
            closure()
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) {
                println("image $instance.imageName:$instance.tag for $instance.name not found locally.Please pull the image and then try again")
                throw new IllegalStateException("image $instance.imageName:$instance.tag for $instance.name not found locally.Please pull the image and then try again")
            }
            if (e.statusCode == 409) {
                println("Container with name $instance.name is already running.Please terminate this to proceed further ")
                throw new IllegalStateException("Container with name $instance.name is already running.Please terminate this to proceed further")
            }
        }

    }
}