package org.kaddiya.grorchestrator.managers

import groovyx.net.http.HTTPBuilder
import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 24/06/16.
 */
abstract class DockerRemoteAPI {

    final Instance instance;

    final String baseUrl;

    final String apiUrl;

    final HTTPBuilder client;

    public DockerRemoteAPI(Instance instance) {
        this.instance = instance
        //construct the baseURL
        this.baseUrl = "http://$instance.host.ip:$instance.host.dockerPort"
        this.client = new HTTPBuilder(baseUrl)
    }


}