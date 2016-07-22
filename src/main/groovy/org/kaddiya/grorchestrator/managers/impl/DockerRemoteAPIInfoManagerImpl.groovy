package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
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
    }

    @Override
    String getInfo() {
        println("finding the info for the host of $protocol://$instance.host.ip")
        def res = this.client.get(path: "/info")
        println(res)
        return res
    }
}