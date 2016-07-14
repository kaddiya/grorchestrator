package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import groovyx.net.http.Method
import net.sf.json.JSON
import org.kaddiya.grorchestrator.managers.DockerContainerRemoveManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse
import groovyx.net.http.RESTClient

/**
 * Created by Webonise on 14/07/16.
 */
@CompileStatic
class DockerContainerRemoveManagerImpl extends DockerRemoteAPI implements DockerContainerRemoveManager {

    @Inject
    DockerContainerRemoveManagerImpl(@Assisted Instance instance) {
        super(instance)
    }

    @Override
    void removeContainer() {
        def url = "http://" + this.instance.host.ip + ":" + this.instance.host.dockerPort
        RESTClient new_client = new RESTClient(url)
        println("deleting the instance with $instance.name")
        this.tryCatchClosure {
            new_client.delete(path: "/containers/$instance.name")
        }
    }
}
