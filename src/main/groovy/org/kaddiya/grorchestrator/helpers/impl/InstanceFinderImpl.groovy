package org.kaddiya.grorchestrator.helpers.impl

import org.kaddiya.grorchestrator.helpers.InstanceFinder
import org.kaddiya.grorchestrator.models.core.latest.Component
import org.kaddiya.grorchestrator.models.core.latest.GrorProject
import org.kaddiya.grorchestrator.models.core.latest.Instance

/**
 * Created by Webonise on 02/07/16.
 */
class InstanceFinderImpl implements InstanceFinder {

    @Override
    Instance getInstanceToInteractWith(GrorProject project, String instanceName) {
        List<Instance> requestedInstances = project.components.collectNested { Component it ->
            it.instances.findAll() { Instance inst ->
                inst.name == instanceName
            }
        }.flatten().grep({ it != null })

//        println(instances.size())
        if (requestedInstances.size() < 1) {
            throw new IllegalArgumentException("No instances with $instanceName detected")
        }
        if (requestedInstances.size() > 1) {
            throw new IllegalArgumentException("Multiple instances with $instanceName detected")
        }
        return requestedInstances.get(0)
    }
}
