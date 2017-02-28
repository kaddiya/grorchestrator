package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.util.logging.Log4j
import okhttp3.Request
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.responses.containerinfo.DockerContainerInspectionResponse

/**
 * Created by Webonise on 22/07/16.
 */
@Log4j
class InspectContainerImpl extends DockerRemoteAPI<DockerContainerInspectionResponse> {

    @Inject
    InspectContainerImpl(@Assisted Instance instance, @Assisted Host host) {
        super(instance, host)
        this.pathSegment = "/containers/$instance.name/json"
    }


    @Override
    Request constructRequest() {
        return new Request.Builder()
                .url(getCanonicalURL(this.pathSegment))
                .get()
                .build();
    }


    @Override
    DockerContainerInspectionResponse doWork() {
        return (super.doWork() as DockerContainerInspectionResponse)
    }
}
