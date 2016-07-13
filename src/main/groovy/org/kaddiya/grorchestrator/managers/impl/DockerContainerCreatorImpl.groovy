package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse

import static groovyx.net.http.ContentType.JSON

/**
 * Created by Webonise on 05/07/16.
 */
//@CompileStatic
class DockerContainerCreatorImpl extends DockerRemoteAPI implements DockerContainerCreator {

    @Inject
    public DockerContainerCreatorImpl(@Assisted Instance instance) {
        super(instance)
    }

    @Override
    DockerContainerCreationResponse createContainer() {

        def value = this.instance.imageName

        println("creating a new instance for $instance.name with image $instance.imageName:$instance.tag")

        tryCatchClosure {
            DockerContainerCreationResponse response
            response = this.client.post(
                    requestContentType: JSON,
                    path: "/containers/create",
                    query: [
                            'name': instance.name
                    ],
                    body: ['Image': value]

            ) as DockerContainerCreationResponse
            response
        }
    }
}
