package org.kaddiya.grorchestrator.managers

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericNoContentResponse
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericOKResponse
import org.kaddiya.grorchestrator.models.ssl.DockerSslSocket
import org.kaddiya.grorchestrator.ssl.SslSocketConfigFactory

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * Created by Webonise on 24/06/16.
 */
abstract class DockerRemoteAPI<DOCKER_REMOTE_RESPONSE_CLASS> {

    final Instance instance;

    final Host host;

    final String baseUrl;

    final OkHttpClient httpClient

    def Closure<DOCKER_REMOTE_RESPONSE_CLASS> doSynchonousHTTPCall

    final Gson gson = new Gson()

    final Class aClass

    public DockerRemoteAPI(Instance instance,Host host) {

        String protocol = derieveProtocol(host)
        this.host = host
        this.instance = instance
        //construct the baseURL
        this.baseUrl = "$protocol://$host.ip:$host.dockerPort"
        //initialise the OkHTTPCLient
        this.httpClient = initialiseOkHTTPClient()
        this.aClass = DOCKER_REMOTE_RESPONSE_CLASS.class
        this.doSynchonousHTTPCall = scrubResponse << apiCallClosure
    }


    String derieveProtocol(Host host) {
        host.protocol ? host.protocol : "http"
    }

    public OkHttpClient initialiseOkHTTPClient() {
        OkHttpClient okClient
        if (this.host.protocol == 'https') {
            String certPath = this.host.certPathForDockerDaemon
            if (!certPath) {
                throw new IllegalStateException("protocol specified is https for host $host.ip but the certificate path is not supplied")
            }
            SslSocketConfigFactory socketConfigFactory = new SslSocketConfigFactory()
            DockerSslSocket socket = socketConfigFactory.createDockerSslSocket(certPath)
            //this client has got AllowAllHostNameConfig.Need to change it soon
            okClient = new OkHttpClient.Builder()
                    .sslSocketFactory(socket.sslSocketFactory, socket.trustManager)
                    .hostnameVerifier(new HostnameVerifier() {
                @Override
                boolean verify(String s, SSLSession sslSession) {
                    return true
                }
            }).build();

            return okClient
        } else {
            okClient = new OkHttpClient.Builder().build()
        }

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
        println(value)
        //first try to attempt to parse the json into the known class.If it throws an exception wrap the response as a string into a genric ok response and send it back
        try {
            return gson.fromJson(value, DOCKER_REMOTE_RESPONSE_CLASS.class);
        } catch (Exception e) {
            println("Something went wrong in the parsing of the response.Going to return a Generic response with actual response wrapped in")
            return new DockerRemoteGenericOKResponse(value)
        }
    }

    protected DockerRemoteGenericNoContentResponse parseJsonResponseFor204() {
        return new DockerRemoteGenericNoContentResponse("the requested action for the $instance.name has been succesfully completed")
    }

    protected abstract Request constructRequest()

}