package org.kaddiya.grorchestrator.managers

import com.google.gson.Gson
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
abstract class DockerRemoteAPI<DOCKER_REMOTE_RESPONSE_CLASS> {

    final Instance instance;

    final String baseUrl;

    final OkHttpClient httpClient

    String actionToPerform

    def Closure<Response> doSynchonousHTTPCall

    final Gson gson = new Gson()

    public DockerRemoteAPI(Instance instance) {
        String protocol = derieveProtocol(instance)
        this.instance = instance
        //construct the baseURL
        this.baseUrl = "$protocol://$instance.host.ip:$instance.host.dockerPort"
        //initialise the OkHTTPCLient
        this.httpClient = initialiseOkHTTPClient()

        this.doSynchonousHTTPCall =  scrubResponse << apiCallClosure
    }


    String derieveProtocol(Instance instance) {
        instance.host.protocol ? instance.host.protocol : "http"
    }

    def tryCatchClosure(Closure closure) {
            closure()
    }

    public OkHttpClient initialiseOkHTTPClient() {
        SslSocketConfigFactory socketConfigFactory = new SslSocketConfigFactory()
        DockerSslSocket socket = socketConfigFactory.createDockerSslSocket(System.getProperty("cert_path"))
        //this client has got AllowAllHostNameConfig.Need to change it soon
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

    public <DOCKER_REMOTE_RESPONSE_CLASS> DOCKER_REMOTE_RESPONSE_CLASS doWork() {
        doSynchonousHTTPCall.call()
    }


    def apiCallClosure = {
         this.httpClient.newCall(constructRequest()).execute()
    }

    def scrubResponse = { Response res ->
        switch (res.code()){
            case 404:
                throw new IllegalStateException("not found")
            case 409:
                throw new IllegalStateException("conflict")
            case 200:
            case 201:
            case 204:
              return  parseResponseJson(res)
            default:
                println(res.code())
        }
    }

    protected <DOCKER_REMOTE_RESPONSE_CLASS> DOCKER_REMOTE_RESPONSE_CLASS parseResponseJson(Response response){
        gson.fromJson(response.body().charStream(), DOCKER_REMOTE_RESPONSE_CLASS.class);
    }

    protected abstract Request constructRequest()

    protected abstract String getResponseAsString(Response response)
}