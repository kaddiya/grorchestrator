package org.kaddiya.grorchestrator.models.remotedocker.responses.containerinfo

import groovy.transform.Canonical

/**
 * Created by Webonise on 28/07/16.
 */
@Canonical
class NetworkSettings {
    String GlobalIPv6PrefixLen

    String IPPrefixLen

    String EndpointID

    String LinkLocalIPv6PrefixLen

    String Bridge

    String HairpinMode

    String SandboxKey

    String MacAddress

    List<String> SecondaryIPAddresses

    String NetworkID

    String IPAddress

    List<String> SecondaryIPv6Addresses

    String GlobalIPv6Address

    String LinkLocalIPv6Address

    String Gateway

    String IPv6Gateway

    List<String> PortMapping

    Map<String, List<Object>> Ports

    //not found in 1.9
    String SandboxID
    String Networks
}

