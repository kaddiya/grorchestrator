package org.kaddiya.grorchestrator.models.remotedocker.responses.info

import groovy.transform.Canonical

/**
 * Created by Webonise on 28/07/16.
 */
@Canonical
class RestartPolicy {
     String Name;
    String MaximumRetryCount;
}