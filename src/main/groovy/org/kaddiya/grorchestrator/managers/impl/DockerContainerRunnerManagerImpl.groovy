package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.factory.DockerContainerCreatorFactory
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerContainerRunnerManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 11/07/16.
 */
@CompileStatic
class DockerContainerRunnerManagerImpl extends DockerRemoteAPI implements DockerContainerRunnerManager {

    final DockerContainerCreator containerCreator


    @Inject
    DockerContainerRunnerManagerImpl(@Assisted Instance instance, DockerContainerCreatorFactory creatorFactory) {
        super(instance)
        containerCreator = creatorFactory.create(this.instance)
    }

    @Override
    void runContainer() {
        println(containerCreator.createContainer())
    }

}

