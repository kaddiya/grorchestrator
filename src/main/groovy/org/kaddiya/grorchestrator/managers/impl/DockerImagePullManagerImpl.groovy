package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.helpers.DockerAuthCredentialsBuilder
import org.kaddiya.grorchestrator.managers.DockerImagePullManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 24/06/16.
 */
@CompileStatic
class DockerImagePullManagerImpl extends DockerRemoteAPI implements DockerImagePullManager {

    String authHeaderKey = "X-Registry-Auth"

    @Inject
    DockerAuthCredentialsBuilder builder


    @Inject
    public DockerImagePullManagerImpl(@Assisted Instance instance) {
        super(instance)
    }


    @Override
    String pullImage() {
        String result = doWork()
        println(result)
        result
    }

    @Override
    Request constructRequest() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url("$baseUrl/images/create?fromImage=$instance.imageName:$instance.tag")
                .header("X-Registry-Auth", builder.getbase64EncodedValueForCredentials())
                .post(RequestBody.create(JSON, ""))  //this requires an empty request body
                .build();
    }
}
