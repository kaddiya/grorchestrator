package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import okhttp3.Request
import okhttp3.Response
import org.kaddiya.grorchestrator.managers.DockerContainerRemoveManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 14/07/16.
 */
@CompileStatic
class DockerContainerRemoveManagerImpl extends DockerRemoteAPI implements DockerContainerRemoveManager {

    @Inject
    DockerContainerRemoveManagerImpl(@Assisted Instance instance) {
        super(instance)
        this.actionToPerform = "Removing a container"
    }

    @Override
    void removeContainer() {
        println("deleting the instance with $instance.name")
        doWork()
    }

    @Override
    Request constructRequest() {
        return new Request.Builder()
                .url("$baseUrl/containers/$instance.name")
                .delete()  //this requires an empty request body
                .build();
    }

    @Override
    protected String getResponseAsString(Response response) {
        String value = response.body().string()
        String result = "response for $actionToPerform: $value"
        return  result
    }
}
