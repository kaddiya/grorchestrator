package org.kaddiya.grorchestrator.managers.impl

import groovy.transform.CompileStatic
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

    public DockerImagePullManagerImpl(Instance instance){
        super(instance)
        this.apiUrl = getBaseUrl()+"/images/create?fromImage=$instance.imageName:$instance.tag"
        headers = new HashMap<>()

        DockerAuthCredentialsBuilder builder =  new DockerAuthCredentialsBuilder()
        String base64EncodedCreds = builder.getbase64EncodedValueForCredentials()
        headers.put(authHeaderKey,base64EncodedCreds)
  }

    @Override
    void pullImage() {
        String base64EncodedDockerCreds = headers.get(authHeaderKey)
        String headerString  = "$authHeaderKey:$base64EncodedDockerCreds"
        "curl -X POST -H $headerString $apiUrl".execute()

    }
}
