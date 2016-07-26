package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import okhttp3.Request
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.DockerRemoteAPIInfoManager
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 22/07/16.
 */
class DockerRemoteAPIInfoManagerImpl extends DockerRemoteAPI implements DockerRemoteAPIInfoManager {

    @Inject
    DockerRemoteAPIInfoManagerImpl(@Assisted Instance instance) {
        super(instance)
        this.path = "/info"
    }

    @Override
    String getInfo() {
        Request request = this.getRequest()
        def info = this.httpClient.newCall(request).execute();
        println(info.body().string())
        return info
    }
}
