package org.kaddiya.grorchestrator.models

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode

/**
 * Created by Webonise on 23/06/16.
 */
@Canonical
@EqualsAndHashCode
class Instance {
    String name
    String imageName
    String tag
    Host host
    Map<String, String> volumeMapping
    Map<Integer, Integer> portMapping
    Map<String, String> hostsMapping
}
