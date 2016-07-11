package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import groovyx.net.http.HTTPBuilder
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 05/07/16.
 */
@CompileStatic
class DockerContainerCreatorImpl extends DockerRemoteAPI implements DockerContainerCreator {

    final HTTPBuilder client

    @Inject
    public DockerContainerCreatorImpl(@Assisted Instance instance) {
        super(instance)
        this.client = new HTTPBuilder(baseUrl)

    }

    @Override
    void createContainer(String imageName, String tag) {

    }
}
