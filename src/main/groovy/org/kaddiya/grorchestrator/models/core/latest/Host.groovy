package org.kaddiya.grorchestrator.models.core.latest

import groovy.transform.Canonical
import org.kaddiya.grorchestrator.models.HostType

/**
 * Created by Webonise on 23/06/16.
 */
@Canonical
class Host {
    String ip
    String alias
    Integer dockerPort
    String protocol
    String dockerVersion
    String apiVersion
    String certPathForDockerDaemon
    HostType hostType
}
