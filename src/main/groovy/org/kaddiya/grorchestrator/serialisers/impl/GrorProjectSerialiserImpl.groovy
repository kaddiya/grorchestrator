package org.kaddiya.grorchestrator.serialisers.impl

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.GrorProject
import org.kaddiya.grorchestrator.serialisers.GrorProjectSerialiser

/**
 * Created by Webonise on 24/06/16.
 */

class GrorProjectSerialiserImpl implements GrorProjectSerialiser {

    @Override
    GrorProject constructGrorProject(File name) {
        if(!name)
            throw new IllegalStateException("Filename cant be null")
        if(!name.exists())
            throw new IllegalStateException("File $name doesnt exist")

        def slurper = new JsonSlurper()
        def projectMap = slurper.parseText(name.text)
        def grorProject = new GrorProject(projectMap)

        return grorProject;

    }
}
