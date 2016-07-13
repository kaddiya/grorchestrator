package org.kaddiya.grorchestrator.helpers.impl

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.helpers.DockerContainerCreationRequestBuilder
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.DockerContainerCreationRequest

/**
 * Created by Webonise on 13/07/16.
 */
@CompileStatic
class DockerContainerCreationRequestBuilderImpl implements  DockerContainerCreationRequestBuilder{
    @Override
    DockerContainerCreationRequest getContainerCreationRequest(Instance instance) {
        DockerContainerCreationRequest request = new DockerContainerCreationRequest()
        request.Image = instance.imageName+":"+instance.tag
        return  request
    }
}
