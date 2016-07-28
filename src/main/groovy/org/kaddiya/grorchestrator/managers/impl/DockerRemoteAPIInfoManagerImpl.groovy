package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import okhttp3.Request
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.DockerRemoteAPIInfoManager
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.info.DockerContainerInspectionResponse

/**
 * Created by Webonise on 22/07/16.
 */
class DockerRemoteAPIInfoManagerImpl extends DockerRemoteAPI<DockerContainerInspectionResponse> implements DockerRemoteAPIInfoManager {

    @Inject
    DockerRemoteAPIInfoManagerImpl(@Assisted Instance instance) {
        super(instance)
    }

    @Override
    Request constructRequest() {
        return new Request.Builder()
                .url("$baseUrl/containers/$instance.name/json")
                .build();
    }


    @Override
    String getInfo() {
        DockerContainerInspectionResponse result = doWork()
        return "status of $instance.name is $result.State.Status and has been started at $result.State.StartedAt"

    }
}
