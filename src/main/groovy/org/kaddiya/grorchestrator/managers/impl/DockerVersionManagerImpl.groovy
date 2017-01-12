package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import okhttp3.MediaType
import okhttp3.Request
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.DockerVersionManager
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.previous.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.dockermetadata.VersionResponse

/**
 * Created by Webonise on 08/09/16.
 */
class DockerVersionManagerImpl extends DockerRemoteAPI<VersionResponse> implements DockerVersionManager {

    @Inject
    DockerVersionManagerImpl(Instance instance,Host host) {
        super(instance,host)
    }

    @Override
    protected Request constructRequest() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url("$baseUrl/version")
                .build();
    }

    @Override
    VersionResponse getDockerVersion() {
        println("getting the docker version")
        return doWork()
    }
}
