package org.kaddiya.grorchestrator.managers

import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.ssl.DockerSslSocket
import org.kaddiya.grorchestrator.ssl.SslSocketConfigFactory

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * Created by Webonise on 24/06/16.
 */
abstract class DockerRemoteAPI {

    final Instance instance;

    final String baseUrl;

    // final HTTPBuilder client;

    final RESTClient restClient;

    final String protocol

    final OkHttpClient httpClient

    public DockerRemoteAPI(Instance instance) {
        this.protocol = derieveProtocol(instance)
        this.instance = instance
        //construct the baseURL

        this.baseUrl = "$protocol://$instance.host.ip:$instance.host.dockerPort"

        //need to deprecate the HTTPBUILEr
        //  this.client = new HTTPBuilder(baseUrl)
        this.httpClient = initOkHTTP()

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

    public OkHttpClient initOkHTTP() {
        SslSocketConfigFactory f = new SslSocketConfigFactory()
        DockerSslSocket socket = f.createDockerSslSocket(System.getProperty("cert_path"))
        OkHttpClient okClient = new OkHttpClient.Builder()
                .sslSocketFactory(socket.sslSocketFactory, socket.trustManager)
                .hostnameVerifier(new HostnameVerifier() {
            @Override
            boolean verify(String s, SSLSession sslSession) {
                return true
            }
        }).build();

        return okClient
    }

    public Response doWork(Request constructedRequest) {
        this.tryCatchClosure {
            return this.httpClient.newCall(constructedRequest).execute()
        }
    }

    protected abstract Request constructRequest()
}