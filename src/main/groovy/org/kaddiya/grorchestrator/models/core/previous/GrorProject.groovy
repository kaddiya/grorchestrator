package org.kaddiya.grorchestrator.models.core.previous

import groovy.transform.Canonical
import org.kaddiya.grorchestrator.models.core.SystemInfo


/**
 * Created by Webonise on 19/03/16.
 */
@Canonical
class GrorProject {
    SystemInfo systemInfo
    List<Component> components
}
