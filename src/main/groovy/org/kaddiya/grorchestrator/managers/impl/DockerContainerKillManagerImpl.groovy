package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovyx.net.http.HttpResponseException
import org.kaddiya.grorchestrator.managers.DockerContainerKillManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 12/07/16.
 */

class DockerContainerKillManagerImpl extends DockerRemoteAPI implements DockerContainerKillManager {

    @Inject
    DockerContainerKillManagerImpl(@Assisted Instance instance) {
        super(instance)
    }

    @Override
    void killContainer() {
            try {
              def response =  this.client.post(
                        path: "/containers/$instance.name/kill"
                )
            }catch (HttpResponseException e){
                if(e.statusCode == 404){
                    throw new IllegalStateException("Container with the name $instance.name not found")
                }
            }
    }
}

