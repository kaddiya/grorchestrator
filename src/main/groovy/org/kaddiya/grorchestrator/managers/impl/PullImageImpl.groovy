package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.helpers.impl.DockerhubAuthCredentialsBuilder
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.PullImage
import org.kaddiya.grorchestrator.models.core.DockerHubAuth
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericOKResponse

/**
 * Created by Webonise on 24/06/16.
 */
@CompileStatic
class PullImageImpl extends DockerRemoteAPI<DockerRemoteGenericOKResponse> implements PullImage {

    String authHeaderKey = "X-Registry-Auth"

    @Inject
    private DockerhubAuthCredentialsBuilder builder
    private final DockerHubAuth auth
    private String pathUrl

    @Inject
    public PullImageImpl(@Assisted Instance instance, @Assisted Host host, @Assisted DockerHubAuth auth) {
        super(instance, host)
        this.auth = auth
        this.pathUrl = "images/create?fromImage=$instance.imageName&tag=$instance.tag"
    }


    @Override
    String pullImage() {
        String result = doWork()
        result
    }

    @Override
    Request constructRequest() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        System.out.println(getCanonicalURL(this.pathUrl))
        return new Request.Builder()
                .url(getCanonicalURL(this.pathUrl))
                .header("X-Registry-Auth", builder.getbase64EncodedValueForCredentials(auth))
                .post(RequestBody.create(JSON, ""))  //this requires an empty request body
                .build();
    }


}
