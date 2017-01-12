package org.kaddiya.grorchestrator.models.core.latest

import groovy.transform.Canonical

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
}
