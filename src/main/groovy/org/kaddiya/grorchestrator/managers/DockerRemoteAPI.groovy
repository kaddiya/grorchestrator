package org.kaddiya.grorchestrator.managers

import com.google.gson.Gson
import groovy.util.logging.Slf4j
import okhttp3.*
import org.kaddiya.grorchestrator.managers.interfaces.DockerRemoteInterface
import org.kaddiya.grorchestrator.models.HostType
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericNoContentResponse
import org.kaddiya.grorchestrator.models.ssl.DockerSslSocket
import org.kaddiya.grorchestrator.ssl.SslSocketConfigFactory
import org.kaddiya.grorchestrator.unix.UnixSocketConnectionFactory
import org.kaddiya.grorchestrator.unix.UnixSocketUtils

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession


@Slf4j
abstract class DockerRemoteAPI<DOCKER_REMOTE_RESPONSE_CLASS> implements DockerRemoteInterface {

    final Instance instance;

    final Host host;

    final OkHttpClient httpClient

    def Closure<DOCKER_REMOTE_RESPONSE_CLASS> doSynchonousHTTPCall

    final Gson gson = new Gson()

    final UnixSocketUtils utils = new UnixSocketUtils()

    final StringBuilder actionStringBuilder;

    protected String pathSegment

    public DockerRemoteAPI(Instance instance, Host host) {

        this.host = host
        this.instance = instance

        //initialise the OkHTTPCLient
        this.httpClient = initialiseOkHTTPClient()
        this.doSynchonousHTTPCall = scrubResponse << apiCallClosure
    }


    public OkHttpClient initialiseOkHTTPClient() {
        OkHttpClient okClient
        if (this.host.hostType == HostType.TCP) {
            if (this.host.protocol == "https") {
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
        } else if (this.host.hostType == HostType.UNIX) {
            UnixSocketConnectionFactory unixConnectionFactory = new UnixSocketConnectionFactory()
            okClient = new OkHttpClient.Builder()
                    .socketFactory(unixConnectionFactory)
                    .dns(unixConnectionFactory)
                    .build()

            return okClient

        }



        return okClient
    }

    protected <DOCKER_REMOTE_RESPONSE_CLASS> DOCKER_REMOTE_RESPONSE_CLASS doInternalWork() {
        this.preHook()
        DOCKER_REMOTE_RESPONSE_CLASS result = doSynchonousHTTPCall.call()
        this.postHook()
        return result
    }


    protected abstract void preHook()

    protected abstract void postHook()


    def apiCallClosure = {
        this.httpClient.newCall(constructRequest()).execute()
    }

    def scrubResponse = { Response res ->
        switch (res.code()) {
            case 404:
                this.notFoundHandler()
                break;
            case 409:
                this.conflictHander()
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
        response.body().withCloseable { ResponseBody body ->
            String value = body.string()
            //first try to attempt to parse the json into the known class.If it throws an exception wrap the response as a string into a genric ok response and send it back
            try {
                return gson.fromJson(body.string(), DOCKER_REMOTE_RESPONSE_CLASS.class);
            } catch (Exception e) {
                log.warn("Something went wrong in the parsing of the response.Going to return a Generic response with actual response wrapped in a Generic Response")
                return new DockerRemoteGenericNoContentResponse(value)
            }

        }
    }

    protected DockerRemoteGenericNoContentResponse parseJsonResponseFor204() {
        return new DockerRemoteGenericNoContentResponse("the requested action for the $instance.name has been succesfully completed")
    }

    protected abstract Request constructRequest()

    protected Object notFoundHandler() {
        throw new IllegalStateException("not found")
    }

    protected Object conflictHander() {
        throw new IllegalStateException("conflict!")
    }

    protected String getCanonicalURL(String path) {

        if (this.host.hostType == HostType.TCP || this.host.hostType == null) {
            return getDecodedUrl(new HttpUrl.Builder()
                    .scheme(this.host.protocol)
                    .host(this.host.ip)
                    .port(this.host.dockerPort as int)
                    .addPathSegment(path).build())
        } else if (this.host.hostType == HostType.UNIX) {
            return getDecodedUrl(new HttpUrl.Builder()
                    .scheme(this.host.protocol)
                    .host(utils.encodeHostname("/var/run/docker.sock"))
                    .addPathSegment(path)
                    .build());
        } else {
            throw new UnsupportedOperationException("$host.hostType is not yet supported")
        }

    }

    private String getDecodedUrl(HttpUrl encodedUrl) {
        return java.net.URLDecoder.decode(encodedUrl.toString(), "UTF-8")
    }
}