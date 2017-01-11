package org.kaddiya.grorchestrator.updators.impl

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.SystemInfo
import org.kaddiya.grorchestrator.models.core.latest.Component
import org.kaddiya.grorchestrator.models.core.latest.GrorProject
import org.kaddiya.grorchestrator.updators.PreviousToLatestSchemaUpdator

/**
 * Created by Webonise on 11/01/17.
 */
@CompileStatic
class PreviousToLatestSchemaUpdatorImpl implements PreviousToLatestSchemaUpdator {
    @Override
    GrorProject updateFromPreviousProject(org.kaddiya.grorchestrator.models.core.previous.GrorProject previousProject) {
        return new GrorProject()
    }
}
