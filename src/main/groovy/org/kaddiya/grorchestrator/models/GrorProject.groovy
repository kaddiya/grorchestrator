package org.kaddiya.grorchestrator.models

import groovy.transform.Canonical
import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * Created by Webonise on 19/03/16.
 */
@Canonical
@ToString
class GrorProject {
    SystemInfo systemInfo
    //List<Host> hosts
    List<Component>components
}
