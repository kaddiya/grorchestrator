package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import okhttp3.Request
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.RemoveContainer
import org.kaddiya.grorchestrator.models.core.previous.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericOKResponse

/**
 * Created by Webonise on 14/07/16.
 */
@CompileStatic
class RemoveContainerImpl extends DockerRemoteAPI<DockerRemoteGenericOKResponse> implements RemoveContainer {

    @Inject
    RemoveContainerImpl(@Assisted Instance instance) {
        super(instance)
    }

    @Override
    void removeContainer() {
        println("deleting the instance with $instance.name")
        println(doWork())
    }

    @Override
    Request constructRequest() {
        return new Request.Builder()
                .url("$baseUrl/containers/$instance.name")
                .delete()
                .build();
    }

}
