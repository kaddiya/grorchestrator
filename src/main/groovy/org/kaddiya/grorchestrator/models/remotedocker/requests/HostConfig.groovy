package org.kaddiya.grorchestrator.models.remotedocker.requests

import groovy.transform.Canonical

/**
 * Created by Webonise on 13/07/16.
 */
@Canonical
class HostConfig {
    List<String> binds
    List<String> links
    Map<String,Map<String,String>> portBindings
    Map<String,String> extraHosts
}
