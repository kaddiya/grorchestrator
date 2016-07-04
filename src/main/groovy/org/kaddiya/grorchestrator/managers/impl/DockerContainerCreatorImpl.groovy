package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import groovyx.net.http.HTTPBuilder
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance

import static groovyx.net.http.ContentType.JSON

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
        if (!tag)
            tag = "latest"

        def value = imageName + ":" + tag

        def response = client.post(
                requestContentType: JSON,
                path: "/containers/create",
                query: [
                        'name': "some-name" //this has to be changed to a UUID name.After deployment this has to be renamed
                ],
                body: ['Image': value]

        )
        println(response)
        response
    }
}
