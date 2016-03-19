package org.kaddiya.grorchestrator.helpers

import groovy.transform.CompileStatic

/**
 * Created by Webonise on 19/03/16.
 */

@CompileStatic
class GrorProjectEnvironmentHelper {

     boolean doesGrorFileExistInCurrentPath(String currentPath){
        System.out.println(currentPath)
    }



    File createGrorFile(File currentPath){
        assert  currentPath : "Current path cant be null"
        assert  currentPath.isDirectory() : "Current path is not  a directory"
        File grorJson =  new File(currentPath,"gror.json")
        grorJson.createNewFile();
        assert grorJson.isFile() : "File not created"
        return grorJson
     }
}
