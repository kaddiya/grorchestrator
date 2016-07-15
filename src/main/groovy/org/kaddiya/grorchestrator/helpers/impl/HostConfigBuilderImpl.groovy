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
        HostConfig config = new HostConfig()
        config.binds = getBinds(instance)
        // config.Links = getLinks(instance)
        config.PortBindings = getPortBindings(instance)
        config.extraHosts = getExtraHostsMapping(instance)

        return config
    }

    List<String> getBinds(Instance instance) {
        return instance.volumeMapping.collect { k, v ->
            (k + ":" + v).toString()
        }
    }

    List<String> getLinks(Instance instance) {
        return Arrays.asList("")
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
