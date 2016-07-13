package org.kaddiya.grorchestrator.models.remotedocker.requests

import groovy.transform.Canonical

/**
 * Created by Webonise on 13/07/16.
 */
@Canonical
class HostConfig {
    List<String> Binds
    List<String> Links
    Map<String,Map<String,String>> PortBindings
    List<String> extraHosts
}
