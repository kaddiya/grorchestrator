package org.kaddiya.grorchestrator.helpers.impl

import org.kaddiya.grorchestrator.helpers.InstanceFinder
import org.kaddiya.grorchestrator.models.core.Component
import org.kaddiya.grorchestrator.models.core.GrorProject
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 02/07/16.
 */
class InstanceFinderImpl  implements  InstanceFinder{
    @Override
    Instance getInstanceToInteractWith(GrorProject project, String instanceName) {
        List<Instance> requestedInstance = project.components.collectNested {Component it ->
            it.instances.find { Instance inst ->
                inst.name == instanceName
            }
        }.grep({it!=null})

        if(requestedInstance.size() > 1){
            throw new IllegalArgumentException("Multiple instances with the same name detected")
        }
    }
}
