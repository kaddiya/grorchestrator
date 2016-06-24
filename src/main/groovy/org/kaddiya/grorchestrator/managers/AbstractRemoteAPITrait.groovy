package org.kaddiya.grorchestrator.managers

import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 24/06/16.
 */
trait AbstractRemoteAPITrait {


    String getBaseUrl(Instance instance){
        return "http://$instance.host.ip:$instance.host.dockerPort"
    }
}