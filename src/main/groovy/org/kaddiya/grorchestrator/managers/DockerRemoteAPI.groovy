package org.kaddiya.grorchestrator.managers

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerRunContainerResponse
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

    def Closure<DOCKER_REMOTE_RESPONSE_CLASS> doSynchonousHTTPCall

    final Gson gson = new Gson()

    public DockerRemoteAPI(Instance instance) {
        String protocol = derieveProtocol(instance)
        this.instance = instance
        //construct the baseURL
        this.baseUrl = "$protocol://$instance.host.ip:$instance.host.dockerPort"
        //initialise the OkHTTPCLient
        this.httpClient = initialiseOkHTTPClient()

        this.doSynchonousHTTPCall = scrubResponse << apiCallClosure
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
        switch (res.code()) {
            case 404:
                throw new IllegalStateException("not found")
            case 409:
                throw new IllegalStateException("conflict")
            case 200:
            case 201:
                return parseResponseJson(res)
            case 204:
                return parseJsonResponseFor204()
            default:
                throw new IllegalStateException("Unidentified response code $res.code found")
        }
    }

    protected <DOCKER_REMOTE_RESPONSE_CLASS> DOCKER_REMOTE_RESPONSE_CLASS parseResponseJson(Response response) {
      def value = response.body().string()
      //  println(response.body().string())
        println(value)
        gson.fromJson(value, DOCKER_REMOTE_RESPONSE_CLASS.class);
    }

    protected <DOCKER_REMOTE_RESPONSE_CLASS> DOCKER_REMOTE_RESPONSE_CLASS parseJsonResponseFor204(){
        return  new DockerContainerRunContainerResponse("the requested action for the $instance.name has been succesfully completed/")
    }

    protected abstract Request constructRequest()

}