package org.kaddiya.grorchestrator.managers.impl

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.managers.DockerImagePullManager

/**
 * Created by Webonise on 24/06/16.
 */
@CompileStatic
class DockerImagePullManagerImpl implements DockerImagePullManager {

    @Override
    String constructURL(Instance instance) {
        return "http://$instance.host.ip:$instance.host.dockerPort/images/create?fromImage=$instance.imageName:$instance.tag"
    }
}
