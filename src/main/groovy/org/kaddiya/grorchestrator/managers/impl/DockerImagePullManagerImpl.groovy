package org.kaddiya.grorchestrator.managers.impl

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 24/06/16.
 */
@CompileStatic
class DockerImagePullManagerImpl extends DockerRemoteAPI {


    public DockerImagePullManagerImpl(Instance instance){
        super(instance)
        this.apiUrl = getBaseUrl()+"/images/create?fromImage=$instance.imageName:$instance.tag"
    }



}
