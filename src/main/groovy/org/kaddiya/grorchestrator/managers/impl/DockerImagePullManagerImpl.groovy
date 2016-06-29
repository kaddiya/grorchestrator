package org.kaddiya.grorchestrator.managers.impl

import groovy.transform.CompileStatic
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import org.kaddiya.grorchestrator.helpers.DockerAuthCredentialsBuilder
import org.kaddiya.grorchestrator.managers.DockerImagePullManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.DockerHubAuth
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 24/06/16.
 */
@CompileStatic
class DockerImagePullManagerImpl extends DockerRemoteAPI implements DockerImagePullManager {

    String authHeaderKey = "X-Registry-Auth"
    final DockerAuthCredentialsBuilder builder
    final HTTPBuilder client

    public DockerImagePullManagerImpl(Instance instance){
        super(instance)
        this.builder =  new DockerAuthCredentialsBuilder()
        this.client = new HTTPBuilder(this.baseUrl)

  }

    @Override
    String pullImage() {
        def response = client.post(
                path : "/images/create",
                headers: ["X-Registry-Auth":builder.getbase64EncodedValueForCredentials()],
                query:[
                        'fromImage': "$instance.imageName:$instance.tag"
                ]
        )
        response
    }
}
