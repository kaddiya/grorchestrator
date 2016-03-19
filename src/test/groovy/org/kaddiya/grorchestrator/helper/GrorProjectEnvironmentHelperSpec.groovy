package org.kaddiya.grorchestrator.helper

import groovy.util.logging.Slf4j
import org.kaddiya.grorchestrator.helpers.GrorProjectEnvironmentHelper
import org.kaddiya.grorchestrator.models.GrorProject
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 19/03/16.
 */
@Slf4j
class GrorProjectEnvironmentHelperSpec extends Specification {

    @Shared
    GrorProjectEnvironmentHelper environmentHelper = new GrorProjectEnvironmentHelper();
    @Shared
    File grorMasterDirectory

    static final String TEST_PROJECT_NAME = "/gror-dog-fooding"

    def "In a blank directory a new gror.json should be created"(){
        given :
         createDirectoryFactory()
        when  :
           File grorFile =  environmentHelper.createGrorFile(grorMasterDirectory)
        then  :
            assert  grorFile : "Gror file not created"
    }

    def "after a gror file is created,a new object of Gror Project has to be created"(){
        given:
            createDirectoryFactory()
            environmentHelper.createGrorFile(grorMasterDirectory)
        when:
          GrorProject project =  environmentHelper.createGrorProject(grorMasterDirectory,TEST_PROJECT_NAME)
        then:
            assert  project : "Project not created"
            assert  project.systemInfo.name.equals(TEST_PROJECT_NAME)


    }

    def createDirectoryFactory(){
        String pathName = System.getProperty("user.dir")+TEST_PROJECT_NAME
        grorMasterDirectory = new File(pathName)
        grorMasterDirectory.mkdir()
        return pathName
    }

    def cleanup(){
        grorMasterDirectory.deleteDir()
    }
}
