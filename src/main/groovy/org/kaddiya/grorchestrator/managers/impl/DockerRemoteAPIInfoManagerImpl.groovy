package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import okhttp3.Request
import okhttp3.Response
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.DockerRemoteAPIInfoManager
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericOKResponse

/**
 * Created by Webonise on 22/07/16.
 */
class DockerRemoteAPIInfoManagerImpl extends DockerRemoteAPI<DockerRemoteGenericOKResponse> implements DockerRemoteAPIInfoManager {

    @Inject
    DockerRemoteAPIInfoManagerImpl(@Assisted Instance instance) {
        super(instance)
    }

    @Override
    Request constructRequest() {
        return new Request.Builder()
                .url("$baseUrl/info")
                .build();
    }


    @Override
    String getInfo() {
        Response result = doWork()
        return ""

    }
}
