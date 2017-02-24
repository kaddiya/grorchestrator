package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import groovy.util.logging.Log4j
import okhttp3.Request
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.DockerVersionManager
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.previous.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.dockermetadata.VersionResponse

/**
 * Created by Webonise on 08/09/16.
 */
@Log4j
class DockerVersionManagerImpl extends DockerRemoteAPI<VersionResponse> implements DockerVersionManager {

    @Inject
    DockerVersionManagerImpl(Instance instance, Host host) {
        super(instance, host)
        this.pathUrl = "version"
    }


    @Override
    protected Request constructRequest() {
        return new Request.Builder()
                .url(getCanonicalURL(this.pathUrl))
                .get()
                .build();
    }

    @Override
    VersionResponse getDockerVersion() {
        return doWork()
    }
}
