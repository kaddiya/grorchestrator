package org.kaddiya.grorchestrator.models

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode

/**
 * Created by Webonise on 19/03/16.
 */
@Canonical
@EqualsAndHashCode
class GrorProject {
    SystemInfo systemInfo
    List<Component> components
}
