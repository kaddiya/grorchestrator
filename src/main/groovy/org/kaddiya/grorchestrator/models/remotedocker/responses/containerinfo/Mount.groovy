package org.kaddiya.grorchestrator.models.remotedocker.responses.containerinfo

import groovy.transform.Canonical

/**
 * Created by Webonise on 28/07/16.
 */
@Canonical
class Mount {
    String Source
    String Destination
    String Mode
    Boolean RW
    String Propagation
}
