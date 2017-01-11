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
        List<Host> newHostList = previousProject.components.collectNested { Component it -> it.instances.collectNested {Instance i -> i.host}}
        println(newHostList)
        return new GrorProject(previousProject.getSystemInfo())
    }
}
