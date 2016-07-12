package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.managers.DockerContainerKillManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 12/07/16.
 */
@CompileStatic
class DockerContainerKillManagerImpl extends DockerRemoteAPI implements DockerContainerKillManager {

    @Inject
    DockerContainerKillManagerImpl(@Assisted Instance instance) {
        super(instance)
    }

    @Override
    void killContainer() {
        tryCatchClosure {
            this.client.post(
                    path: "/containers/$instance.name/kill"
            )
        }

    }
}

