package org.kaddiya.grorchestrator.models.core

import groovy.transform.Canonical

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
    Map<String,String>envVars
}
