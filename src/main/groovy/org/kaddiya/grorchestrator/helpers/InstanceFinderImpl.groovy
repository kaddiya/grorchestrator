package org.kaddiya.grorchestrator.helpers

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.Component
import org.kaddiya.grorchestrator.models.core.GrorProject
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 02/07/16.
 */
@CompileStatic
class InstanceFinderImpl  implements InstanceFinder{


    Instance getInstanceToInteractWith(GrorProject project,String instanceName){

        List<Instance> requestedInstances = project.components.collectNested {Component it ->
            it.instances.find { Instance inst ->
                inst.name == instanceName
            }
        }.grep({it!=null})

        if(requestedInstances.size() != 1){
            throw new IllegalArgumentException("Multiple instances of the same name detected")
        }
        return  requestedInstances.get(0)
    }
}
