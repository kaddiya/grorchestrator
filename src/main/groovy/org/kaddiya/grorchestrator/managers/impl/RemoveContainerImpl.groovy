package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import groovy.util.logging.Slf4j
import okhttp3.Request
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.interfaces.RemoveContainer
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericOKResponse

/**
 * Created by Webonise on 14/07/16.
 */
@CompileStatic
@Log4j
class RemoveContainerImpl extends DockerRemoteAPI<DockerRemoteGenericOKResponse> implements RemoveContainer {

    @Inject
    RemoveContainerImpl(@Assisted Instance instance, @Assisted Host host) {
        super(instance, host)
        this.pathUrl = "/containers/$instance.name"
    }

    @Override
    void removeContainer() {
        log.info("deleting the instance with $instance.name")
        doWork()
    }

    @Override
    Request constructRequest() {
        return new Request.Builder()
                .url(getCanonicalURL(pathUrl))
                .delete()
                .build();
    }

}
