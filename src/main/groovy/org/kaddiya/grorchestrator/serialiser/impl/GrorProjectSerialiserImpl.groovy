package org.kaddiya.grorchestrator.serialiser.impl

import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.serialiser.GrorProjectSerialiser

/**
 * Created by Webonise on 11/01/17.
 */
@CompileStatic
public class GrorProjectSerialiserImpl implements GrorProjectSerialiser {

    @Override
    void serialiseProjectToFile(Object project, String fileName) {
        File file = new File(fileName)
        file.write(new JsonBuilder(project).toPrettyString())
    }
}
