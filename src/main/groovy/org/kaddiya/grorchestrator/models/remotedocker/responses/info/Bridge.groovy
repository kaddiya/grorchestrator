package org.kaddiya.grorchestrator.models.remotedocker.responses.info

import groovy.transform.Canonical

/**
 * Created by Webonise on 28/07/16.
 */
@Canonical
class Bridge {
    String IPAMConfig
    List<String> Links
    List<String> Aliases
    String NetworkID
    String EndpointID
    String Gateway
    String IPAddress
    String IPPrefixLen
    String IPv6Gateway
    String GlobalIPv6Address
    Integer GlobalIPv6PrefixLen
    String MacAddress
}
