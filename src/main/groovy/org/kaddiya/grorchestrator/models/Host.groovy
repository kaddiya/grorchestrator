package org.kaddiya.grorchestrator.models

import groovy.transform.Immutable

/**
 * Created by Webonise on 23/06/16.
 */
@Immutable
class Host {
    String ip
    String alias
    Integer dockerPort
}
