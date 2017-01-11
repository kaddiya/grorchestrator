package org.kaddiya.grorchestrator.models.core.latest

import groovy.transform.Canonical
import org.kaddiya.grorchestrator.models.core.SystemInfo

/**
 * Created by Webonise on 11/01/17.
 */
@Canonical
class GrorProject {
    SystemInfo systemInfo
    List<Component> components
    List<Host> hosts
}
