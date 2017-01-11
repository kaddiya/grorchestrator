package org.kaddiya.grorchestrator.models.core.previous

import groovy.transform.Canonical
import org.kaddiya.grorchestrator.models.core.latest.Host

/**
 * Created by Webonise on 23/06/16.
 */
@Canonical
class Instance {
    String name
    String imageName
    String tag
    Host host
    Map<String, String> volumeMapping
    Map<Integer, Integer> portMapping
    Map<String, String> hostsMapping
    Map<String, String> envMap
    Map<String, String> links
    List<String>volumesFrom
    String commandToBeExecuted
}
