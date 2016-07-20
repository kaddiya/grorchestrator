package org.kaddiya.grorchestrator.models.core

import groovy.transform.Canonical

/**
 * Created by Webonise on 23/06/16.
 */
@Canonical
class Host {
    String ip
    String alias
    Integer dockerPort
}
