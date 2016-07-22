package org.kaddiya.grorchestrator.managers.impl

import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.DockerRemoteAPIInfoManager
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 22/07/16.
 */
class DockerRemoteAPIInfoManagerImpl extends DockerRemoteAPI implements  DockerRemoteAPIInfoManager {

    DockerRemoteAPIInfoManagerImpl(Instance instance) {
        super(instance)
    }

    @Override
    String getInfo() {
        return this.client.get(path: "/info")
    }
}
