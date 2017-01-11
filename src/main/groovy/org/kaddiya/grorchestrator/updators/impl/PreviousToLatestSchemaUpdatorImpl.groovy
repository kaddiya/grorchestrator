package org.kaddiya.grorchestrator.updators.impl

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.latest.GrorProject
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.previous.Component
import org.kaddiya.grorchestrator.models.core.previous.Instance
import org.kaddiya.grorchestrator.updators.PreviousToLatestSchemaUpdator

/**
 * Created by Webonise on 11/01/17.
 */
@CompileStatic
class PreviousToLatestSchemaUpdatorImpl implements PreviousToLatestSchemaUpdator {
    @Override
    GrorProject updateFromPreviousProject(org.kaddiya.grorchestrator.models.core.previous.GrorProject previousProject) {
        //extract the host list
        List<Host> newHostList = getLatestHostListFromPreviousProject(previousProject)

        return new GrorProject(previousProject.getSystemInfo(), Arrays.asList(new org.kaddiya.grorchestrator.models.core.latest.Component()), newHostList)
    }

    @Override
    List<Host> getLatestHostListFromPreviousProject(org.kaddiya.grorchestrator.models.core.previous.GrorProject previousProject) {
        List<Host> result = previousProject.components.collectNested {
            Component it ->
                it.instances.collect {
                    Instance i -> i.host
                }
        }

        return result.flatten() as ArrayList<Host>
    }

    @Override
    List<org.kaddiya.grorchestrator.models.core.latest.Instance> getLatestInstancesListFromPreviousProject(org.kaddiya.grorchestrator.models.core.previous.GrorProject previousProject) {
        List<org.kaddiya.grorchestrator.models.core.latest.Instance> newInstancesList = previousProject.components.collectNested {
            Component it ->
                it.instances.collect { Instance previousInstance ->
                    new org.kaddiya.grorchestrator.models.core.latest.Instance(previousInstance.name, previousInstance.imageName,
                            previousInstance.tag, previousInstance.host.alias, previousInstance.volumeMapping,
                            previousInstance.portMapping, previousInstance.hostsMapping, previousInstance.envMap,
                            previousInstance.links, previousInstance.volumesFrom, previousInstance.commandToBeExecuted)
                }
        }

        return newInstancesList.flatten() as ArrayList<org.kaddiya.grorchestrator.models.core.latest.Instance>
    }
}

