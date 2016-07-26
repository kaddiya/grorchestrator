package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import okhttp3.Request
import org.kaddiya.grorchestrator.guice.factory.DockerContainerRemoveMangerFactory
import org.kaddiya.grorchestrator.managers.DockerContainerKillManager
import org.kaddiya.grorchestrator.managers.DockerContainerRemoveManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 12/07/16.
 */
@CompileStatic
class DockerContainerKillManagerImpl extends DockerRemoteAPI implements DockerContainerKillManager {

    @Inject
    DockerContainerRemoveMangerFactory containerRemoveMangerFactory

    final DockerContainerRemoveManager containerRemoveManager;

    @Inject
    DockerContainerKillManagerImpl(
            @Assisted Instance instance, DockerContainerRemoveMangerFactory containerRemoveMangerFactory) {
        super(instance)
        this.containerRemoveManager = containerRemoveMangerFactory.create(instance)
    }

    @Override
    void killContainer() {
        println("going to kill the container $instance.name")
        tryCatchClosure {
            this.restClient.post(path: "/containers/$instance.name/kill")
        }
        println("finished killing the container.Now going to remove the name $instance.name")
        containerRemoveManager.removeContainer()
    }

    @Override
    Request constructRequest() {
        throw new IllegalStateException("Not yet implemented")
    }
}

