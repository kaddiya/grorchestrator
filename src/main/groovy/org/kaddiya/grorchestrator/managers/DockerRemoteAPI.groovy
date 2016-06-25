package org.kaddiya.grorchestrator.managers

import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 24/06/16.
 */
abstract class DockerRemoteAPI implements DockerRemoteApiManager {

    final Instance instance;

    final String baseUrl;

    public DockerRemoteAPI(Instance instance){
        this.instance = instance
        this.baseUrl = "http://$instance.host.ip:$instance.host.dockerPort"
    }



}