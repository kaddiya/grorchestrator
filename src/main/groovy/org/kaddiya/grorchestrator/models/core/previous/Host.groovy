package org.kaddiya.grorchestrator.models.core.previous

import groovy.transform.Canonical

/**
 * Created by Webonise on 11/01/17.
 */
@Canonical
class Host {
    String ip
    String alias
    Integer dockerPort
    String protocol
    String dockerVersion
    String apiVersion

}
