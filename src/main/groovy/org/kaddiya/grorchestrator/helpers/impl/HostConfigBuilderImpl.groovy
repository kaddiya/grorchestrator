package org.kaddiya.grorchestrator.helpers.impl

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.helpers.HostConfigBuilder
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.HostConfig

/**
 * Created by Webonise on 13/07/16.
 */
@CompileStatic
class HostConfigBuilderImpl implements HostConfigBuilder {

    HostConfig constructHostConfig(Instance instance) {
        HostConfig config
        if(instance.host.apiVersion  == '1.19'){
            config = new HostConfig()
        }
        else{
            config = new HostConfig(getBinds(instance), getLinks(instance), getPortBindings(instance), getExtraHostsMapping(instance))
        }

        return config
    }

    List<String> getBinds(Instance instance) {
        return instance.volumeMapping.collect { k, v ->
            (k + ":" + v).toString()
        }
    }

    List<String> getLinks(Instance instance) {
        instance.links.collect { k, v ->
            k + ":" + v
        }
    }

    Map<String, Map<String, String>> getPortBindings(Instance instance) {
        //m sure there is a fancier way to do this using getPortMappingsFromInstance()
        instance.portMapping.collectEntries { k, v ->
            return [v + "/tcp", Arrays.asList(Collections.unmodifiableMap("HostPort": k.toString()))]
        } as Map<String, Map<String, String>>

    }

    List<String> getExtraHostsMapping(Instance instance) {
        instance.hostsMapping.collect { k, v ->
            k + ":" + v
        }
    }
}
