package org.kaddiya.grorchestrator.models

import groovy.transform.Canonical
import groovy.transform.Immutable

/**
 * Created by Webonise on 23/06/16.
 */
@Canonical
class Instance {
    String instanceName
    String imageName
    String tag
    Host host
    Map<String,String> volumeMapping
    Map<Integer,Integer> portMapping
    Map<String,String> hostsMapping
}
