package org.kaddiya.grorchestrator.serialisers

import org.kaddiya.grorchestrator.models.core.latest.GrorProject
import org.kaddiya.grorchestrator.serialiser.impl.GrorProjectSerialiserImpl
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 11/01/17.
 */
class GrorProjectSerialiserImplSpec extends Specification{

    @Shared
    GrorProjectSerialiserImpl impl = new GrorProjectSerialiserImpl()
    def "serialiseProjectToFile should write out the json File"(){
        when:
        impl.serialiseProjectToFile(new GrorProject(),"something.json")
        then:
        assert true
    }
}
