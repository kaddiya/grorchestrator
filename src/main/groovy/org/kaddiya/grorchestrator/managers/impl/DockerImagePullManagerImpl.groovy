package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import groovyx.net.http.HTTPBuilder
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
    final DockerAuthCredentialsBuilder builder
    final HTTPBuilder client

    @Inject
    public DockerImagePullManagerImpl(@Assisted Instance instance){
        super(instance)
        this.builder =  new DockerAuthCredentialsBuilder()
        this.client = new HTTPBuilder(this.baseUrl)

  }

    @Override
    String pullImage(String imageName,String tag) {
        if(!tag)
            tag = "latesttag"
        def response = client.post(
                path : "/images/create",
                headers: ["X-Registry-Auth":builder.getbase64EncodedValueForCredentials()],
                query:[
                        'fromImage': "$imageName:$tag"
                ]
        )
        response
    }
}
