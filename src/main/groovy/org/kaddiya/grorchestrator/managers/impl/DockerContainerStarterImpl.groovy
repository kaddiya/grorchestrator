package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import groovyx.net.http.HTTPBuilder
import net.sf.json.JSON
import org.kaddiya.grorchestrator.managers.DockerContainerStarter
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse

/**
 * Created by Webonise on 05/07/16.
 */

@CompileStatic
class DockerContainerStarterImpl extends DockerRemoteAPI implements DockerContainerStarter {

    final HTTPBuilder client

    @Inject
    public DockerContainerStarterImpl(@Assisted Instance instance) {
        super(instance)
        this.client = new HTTPBuilder(baseUrl)

    }


    @Override
    void startContainer(Instance instance) {
        String tag
        if (!instance.tag)
            tag = "latest"

        def value = instance.imageName + ":" + tag

        DockerContainerCreationResponse response = client.post(
                requestContentType: JSON,
                path: "/containers/create",
                query: [
                        'name': "some-name-1-1" //this has to be changed to a UUID name.After deployment this has to be renamed
                ],
                body: ['Image': value]

        ) as DockerContainerCreationResponse
        println(response.Id)
        response
    }
}
