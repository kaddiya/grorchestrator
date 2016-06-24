package org.kaddiya.grorchestrator.managers.impl

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.managers.AbstractRemoteAPITrait
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.managers.DockerImagePullManager

/**
 * Created by Webonise on 24/06/16.
 */
@CompileStatic
class DockerImagePullManagerImpl implements DockerImagePullManager,AbstractRemoteAPITrait {

    @Override
    String constructURL(Instance instance) {
        String baseUrl = getBaseUrl(instance)
        return "$baseUrl/images/create?fromImage=$instance.imageName:$instance.tag"
    }
}
