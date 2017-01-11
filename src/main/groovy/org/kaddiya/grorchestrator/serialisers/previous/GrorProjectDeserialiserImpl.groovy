package org.kaddiya.grorchestrator.serialisers.previous

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.previous.GrorProject
import org.kaddiya.grorchestrator.serialisers.GrorProjectDeserialiser

/**
 * Created by Webonise on 24/06/16.
 */
@CompileStatic
class GrorProjectDeserialiserImpl implements GrorProjectDeserialiser {

    @Override
    GrorProject constructGrorProject(File name) {
        if (!name)
            throw new IllegalStateException("Filename cant be null")
        if (!name.exists())
            throw new IllegalStateException("File $name doesnt exist")

        GrorProject grorProject
        ObjectMapper mapper = new ObjectMapper();
        try {
            grorProject = mapper.readValue(name.text, GrorProject.class);
        } catch (Exception e) {
            println("Invalid gror file format.please correct the format")
            throw new IllegalStateException(e.getMessage())
        }

        return grorProject;

    }


}
