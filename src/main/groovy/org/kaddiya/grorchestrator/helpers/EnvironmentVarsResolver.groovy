package org.kaddiya.grorchestrator.helpers

import groovy.transform.CompileStatic

/**
 * Created by Webonise on 18/07/16.
 */
@CompileStatic
class EnvironmentVarsResolver {

    public String getEnvironmentVarValueForKey(String keyName) {
        if (System.getProperty(keyName) != null) {
            return System.getProperty(keyName)
        } else if (System.getenv(keyName)) {
            return System.getenv(keyName)
        } else {
            throw new IllegalStateException("Did not find any env var for $keyName")
        }
    }

}
