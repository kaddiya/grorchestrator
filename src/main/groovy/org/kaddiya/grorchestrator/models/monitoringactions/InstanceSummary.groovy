package org.kaddiya.grorchestrator.models.monitoringactions

import groovy.transform.Immutable

/**
 * Created by Webonise on 13/01/17.
 */
@Immutable
class InstanceSummary {
    String instanceName
    String hostId
    String hostIp
    String imageName
}
