package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.helpers.impl.DockerhubAuthCredentialsBuilder
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.AbstractDockerInteractionResponse
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericOKResponse

@CompileStatic
@Slf4j
class PullImageImpl extends DockerRemoteAPI<DockerRemoteGenericOKResponse> {

    String authHeaderKey = "X-Registry-Auth"

    @Inject
    private DockerhubAuthCredentialsBuilder builder

    @Inject
    public PullImageImpl(@Assisted Instance instance, @Assisted Host host) {
        super(instance, host)
        this.pathSegment = "images/create?fromImage=$instance.imageName&tag=$instance.tag"

    }

    @Override
    protected void preHook() {

    }

    @Override
    protected void postHook() {

    }

    @Override
    Request constructRequest() {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url(getCanonicalURL(this.pathSegment))
                .header("X-Registry-Auth", builder.getBase64EncodedValueForCredsWithAuthId(instance.authId))
                .post(RequestBody.create(JSON, ""))  //this requires an empty request body
                .build();
    }

    @Override
    AbstractDockerInteractionResponse doWork() {
        return (super.doInternalWork() as AbstractDockerInteractionResponse)
    }
}
