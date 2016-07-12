package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import groovyx.net.http.HttpResponseException
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
        def response
        try {
            response = client.post(
                    path: "/images/create",
                    headers: ["X-Registry-Auth": builder.getbase64EncodedValueForCredentials()],
                    query: [
                            'fromImage': "$instance.imageName:$instance.tag"
                    ]
            )
        }
        catch (HttpResponseException e) {
            if (e.statusCode == 404) {
                println("$instance.name not found.Please ensure that the image actually exists")
            }
            if (e.statusCode == 409) {
                println("Container with name $instance.name is already running.Please terminate this to proceed further ")
            }
        }
        response
    }
}
