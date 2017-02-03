package org.kaddiya.grorchestrator.updators.impl

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.Grorchestrator
import org.kaddiya.grorchestrator.models.core.SystemInfo
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

    public final static String DEFAULT_AUTH_ID = "update-your-auth-key"

    @Override
    GrorProject updateFromPreviousProject(org.kaddiya.grorchestrator.models.core.previous.GrorProject previousProject) {
        //extract the host list
        List<Host> newHostList = getLatestHostListFromPreviousProject(previousProject)
        //etract the component list
        List<org.kaddiya.grorchestrator.models.core.latest.Component> newComponents = getLatestComponentsListFromPreviousGrorProject(previousProject)
        //return the new project
        return new GrorProject(new SystemInfo(previousProject.getSystemInfo().getName(), Grorchestrator.CURRENT_GROR_VERSION), newComponents, newHostList)
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
    List<org.kaddiya.grorchestrator.models.core.latest.Component> getLatestComponentsListFromPreviousGrorProject(org.kaddiya.grorchestrator.models.core.previous.GrorProject previousProject) {
        List<org.kaddiya.grorchestrator.models.core.latest.Component> newComponentList = previousProject.components.collect { it ->
            new org.kaddiya.grorchestrator.models.core.latest.Component(it.name, getLatestInstancesListFromPreviousComponents(it))
        }
        return newComponentList
    }

    @Override
    List<org.kaddiya.grorchestrator.models.core.latest.Instance> getLatestInstancesListFromPreviousComponents(org.kaddiya.grorchestrator.models.core.previous.Component previousComponent) {
        List<org.kaddiya.grorchestrator.models.core.latest.Instance> newInstancesList = previousComponent.instances.collect { previousInstance ->
            new org.kaddiya.grorchestrator.models.core.latest.Instance(previousInstance.name, previousInstance.imageName,
                    previousInstance.tag, previousInstance.host.alias, DEFAULT_AUTH_ID, previousInstance.volumeMapping,
                    previousInstance.portMapping, previousInstance.hostsMapping, previousInstance.envMap,
                    previousInstance.links, previousInstance.volumesFrom, previousInstance.commandToBeExecuted)

        }

        return newInstancesList
    }


}

