package org.kaddiya.grorchestrator.updator

import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.previous.GrorProject
import org.kaddiya.grorchestrator.serialisers.previous.GrorProjectSerialiserImpl
import org.kaddiya.grorchestrator.updators.PreviousToLatestSchemaUpdator
import org.kaddiya.grorchestrator.updators.impl.PreviousToLatestSchemaUpdatorImpl
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 11/01/17.
 */
class PreviousToLatestSchemaUpdatorImplSpec extends Specification {

    @Shared
    URL previousGrorJsonUrl = getClass().getClassLoader().getResource("gror.json")
    @Shared
    File previousGrorJsonFile = new File(previousGrorJsonUrl.toURI())
    @Shared
    GrorProjectSerialiserImpl previousProjectSerializer = new GrorProjectSerialiserImpl()
    @Shared
    GrorProject previousProject = previousProjectSerializer.constructGrorProject(previousGrorJsonFile)
    @Shared
    PreviousToLatestSchemaUpdator updatorImpl =  new PreviousToLatestSchemaUpdatorImpl()

    def "updateFromPreviousProject should update properly to the new Version"(){

        when:
        org.kaddiya.grorchestrator.models.core.latest.GrorProject updatedProject = updatorImpl.updateFromPreviousProject(previousProject)

        then:
        assert updatedProject instanceof  org.kaddiya.grorchestrator.models.core.latest.GrorProject
    }

    def "getLatestHostListFromPreviousProject should return a proper list of the host from the previous GrorProject"() {
            when:
            List<Host> newHostList = updatorImpl.getLatestHostListFromPreviousProject(previousProject)
            then:
            assert  newHostList.size() == 1
            assert  newHostList.get(0).equals(new Host("127.0.0.1", "api-vm-1", 2376, "http", "1.7.1", "1.19","/path/to/certs"))
    }

}
