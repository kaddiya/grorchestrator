package org.kaddiya.grorchestrator.updator

import org.kaddiya.grorchestrator.models.core.previous.GrorProject
import org.kaddiya.grorchestrator.serialisers.previous.GrorProjectSerialiserImpl
import org.kaddiya.grorchestrator.updators.PreviousToLatestSchemaUpdator
import org.kaddiya.grorchestrator.updators.impl.PreviousToLatestSchemaUpdatorImpl
import spock.lang.Specification

/**
 * Created by Webonise on 11/01/17.
 */
class PreviousToLatestSchemaUpdatorImplSpec extends Specification {

    def "updateFromPreviousProject should update properly to the new Version"(){
        given:

        URL previousGrorJsonUrl = getClass().getClassLoader().getResource("gror.json")
        File previousGrorJsonFile = new File(previousGrorJsonUrl.toURI())
        GrorProjectSerialiserImpl previousProjectSerializer = new GrorProjectSerialiserImpl()
        GrorProject previousProject = previousProjectSerializer.constructGrorProject(previousGrorJsonFile)
        PreviousToLatestSchemaUpdator updatorImpl =  new PreviousToLatestSchemaUpdatorImpl()

        when:
        org.kaddiya.grorchestrator.models.core.latest.GrorProject updatedProject = updatorImpl.updateFromPreviousProject(previousProject)

        then:
        assert updatedProject instanceof  org.kaddiya.grorchestrator.models.core.latest.GrorProject
    }
}
