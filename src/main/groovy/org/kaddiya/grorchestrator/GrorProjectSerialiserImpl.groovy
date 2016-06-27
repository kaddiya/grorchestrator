package org.kaddiya.grorchestrator

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.DockerHubAuth
import org.kaddiya.grorchestrator.models.core.GrorProject
import org.kaddiya.grorchestrator.serialisers.GrorProjectSerialiser

/**
 * Created by Webonise on 24/06/16.
 */
@CompileStatic
class GrorProjectSerialiserImpl implements GrorProjectSerialiser {

    @Override
    GrorProject constructGrorProject(File name) {
        if (!name)
            throw new IllegalStateException("Filename cant be null")
        if (!name.exists())
            throw new IllegalStateException("File $name doesnt exist")

        /*
              the following just yeilds lazy maps for nested components
        def slurper = new JsonSlurper()
        def projectMap = slurper.parseText(name.text)
        def grorProject = new GrorProject(projectMap)
        */
        //Jackson works like a charm

        ObjectMapper mapper = new ObjectMapper();
        GrorProject grorProject = mapper.readValue(name.text, GrorProject.class);
        return grorProject;

    }

    void prepareEnvironmentVariables(GrorProject project) {
        DockerHubAuth creds = project.getDockerHubAuthCreds()

        checkAndSetSystemProperty("registry.username",creds.username)
        checkAndSetSystemProperty("registry.password",creds.password)
        checkAndSetSystemProperty("registry.auth",creds.auth)
        checkAndSetSystemProperty("registry.email",creds.email)
    }

    void checkAndSetSystemProperty(String propertyName,String propertyValue){

        if(!(propertyValue || System.getProperty(propertyName)) ){
            throw  new IllegalArgumentException("please set the value for $propertyName in the gror file or pass in the value via command line")
        }
        if(!System.getProperty(propertyName)){
            System.setProperty(propertyName,propertyValue)
        }


    }
}
