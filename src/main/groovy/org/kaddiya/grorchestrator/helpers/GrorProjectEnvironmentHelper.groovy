package org.kaddiya.grorchestrator.helpers

import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.Component
import org.kaddiya.grorchestrator.models.GrorProject
import org.kaddiya.grorchestrator.models.SystemInfo

import javax.imageio.stream.FileImageOutputStream

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
        File grorFile =  new File(masterPath,"gror.json")
        assert  grorFile.exists() : "Gror json wasnt created"
        new GrorProject(new SystemInfo(systemName,grorFile.getPath()),new ArrayList<Component>());
    }

    File serialiseGrorProject(GrorProject project,File grorMasterDirectory){
        JsonBuilder builder =  new JsonBuilder(project)
        File grorProjectFile = new File(project.getSystemInfo().getGrorFile())
        grorProjectFile .withWriter {Writer writer ->
            builder.writeTo(writer)
        }
        return grorProjectFile
    }
}
