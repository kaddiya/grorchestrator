package org.kaddiya.grorchestrator.models.core.latest

import groovy.transform.Canonical

/**
 * Created by Webonise on 11/01/17.
 */
@Canonical
class Instance {
    String name
    String imageName
    String tag
    String hostId
    String authId
    Map<String, String> volumeMapping
    Map<Integer, Integer> portMapping
    Map<String, String> hostsMapping
    Map<String, String> envMap
    Map<String, String> links
    List<String> volumesFrom
    String commandToBeExecuted
}
