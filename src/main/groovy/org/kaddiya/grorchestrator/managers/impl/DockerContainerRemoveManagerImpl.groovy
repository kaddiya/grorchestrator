package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import groovyx.net.http.Method
import org.kaddiya.grorchestrator.managers.DockerContainerRemoveManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse

/**
 * Created by Webonise on 14/07/16.
 */
class DockerContainerRemoveManagerImpl extends DockerRemoteAPI implements DockerContainerRemoveManager {

    @Inject
    DockerContainerRemoveManagerImpl(Instance instance) {
        super(instance)
    }

    @Override
    void removeContainer() {
        println("deleting the instance with $instance.name")
        this.tryCatchClosure {
            DockerContainerCreationResponse response
            response = this.client.request(
                    method:Method.DELETE,{ req ->
                    path : "/container/$instance.name"
            })
        }
    }
}
