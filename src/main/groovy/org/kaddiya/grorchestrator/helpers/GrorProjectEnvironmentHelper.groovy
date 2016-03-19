package org.kaddiya.grorchestrator.helpers

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.Component
import org.kaddiya.grorchestrator.models.GrorProject
import org.kaddiya.grorchestrator.models.SystemInfo

/**
 * Created by Webonise on 19/03/16.
 */

@CompileStatic
class GrorProjectEnvironmentHelper {



    File createGrorFile(File currentPath){
        assert  currentPath : "Current path cant be null"
        assert  currentPath.isDirectory() : "Current path is not  a directory"
        File grorJson =  new File(currentPath,"gror.json")
        grorJson.createNewFile();
        assert grorJson.isFile() : "File not created"
        return grorJson
     }

    GrorProject createGrorProject(File masterPath,String systemName){
        assert  new File(masterPath,"gror.json").exists()
        new GrorProject(new SystemInfo(systemName),new ArrayList<Component>());

    }
}
