package org.kaddiya.grorchestrator.deserialisers.latest

import com.fasterxml.jackson.databind.ObjectMapper
import org.kaddiya.grorchestrator.models.core.latest.GrorProject

/**
 * Created by Webonise on 11/01/17.
 */
class GrorProjectDeserialiserImpl {


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
