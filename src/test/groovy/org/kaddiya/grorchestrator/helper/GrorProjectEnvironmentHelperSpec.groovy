package org.kaddiya.grorchestrator.helper

import groovy.util.logging.Slf4j
import org.kaddiya.grorchestrator.helpers.GrorProjectEnvironmentHelper
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 19/03/16.
 */
@Slf4j
class GrorProjectEnvironmentHelperSpec extends Specification {

    @Shared
    GrorProjectEnvironmentHelper environmentHelper = new GrorProjectEnvironmentHelper();

    static final String TEST_PROJECT_NAME = "/sample-project"

    def "In a blank directory a new gror.json should be created"(){
        given :
            String pathName = System.getProperty("user.dir")+TEST_PROJECT_NAME
            File grorMasterDirectory = new File(pathName)
            grorMasterDirectory.mkdir()
        when  :
           File grorFile =  environmentHelper.createGrorFile(grorMasterDirectory)
        then  :
            assert  grorFile : "Gror file not created"
    }


}
