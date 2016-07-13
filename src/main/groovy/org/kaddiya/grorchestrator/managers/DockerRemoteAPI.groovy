package org.kaddiya.grorchestrator.managers

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException
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

    def tryCatchClosure(Closure closure) {
        try {
            closure()
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) {
                println("image $instance.imageName:$instance.tag for $instance.name not found locally.Please pull the image and then try again")
                throw new IllegalStateException("image $instance.imageName:$instance.tag for $instance.name not found locally.Please pull the image and then try again")
            }
            if (e.statusCode == 409) {
                println("Container with name $instance.name is already running.Please terminate this to proceed further ")
                throw new IllegalStateException("Container with name $instance.name is already running.Please terminate this to proceed further")
            }
        }
    }
}