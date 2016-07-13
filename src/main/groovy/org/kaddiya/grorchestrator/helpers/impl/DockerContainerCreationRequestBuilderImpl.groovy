package org.kaddiya.grorchestrator.helpers.impl

import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.helpers.DockerContainerCreationRequestBuilder
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.DockerContainerCreationRequest
import org.kaddiya.grorchestrator.models.remotedocker.requests.HostConfig

import java.util.stream.Collectors

/**
 * Created by Webonise on 13/07/16.
 */
@CompileStatic
class DockerContainerCreationRequestBuilderImpl implements  DockerContainerCreationRequestBuilder{

    @Override
    DockerContainerCreationRequest getContainerCreationRequest(Instance instance) {
        DockerContainerCreationRequest request = new DockerContainerCreationRequest()
        request.image = instance.imageName+":"+instance.tag
        request.exposedPorts = getPortMappingsFromInstance(instance)
        request.volumes = getVolumes(instance)
        request.hostConfig = getHostConfig(instance)
        return  request
    }

    @Override
    Map<String, Object> getPortMappingsFromInstance(Instance instance) {
       Map<String,Object> result = instance.portMapping.collectEntries {k,v->
            return [k+"/tcp",new Object()]
        } as Map<String, Object>
        return  result
    }

    @Override
    Map<String, Object> getVolumes(Instance instance) {
        Map<String,Object> result = instance.volumeMapping.collectEntries {k,v->
            return [k,new Object()]
        } as Map<String, Object>
        return  result
    }

    @Override
    HostConfig getHostConfig(Instance instance) {
        HostConfig config = new HostConfig()
      //  config.binds = getBinds(instance)
        config.Links = getLinks(instance)
       // config.portBindings = getPortBindings(instance)
        return config
    }


    List<String>getBinds(Instance instance){
     return Arrays.asList(instance.volumeMapping.inspect().replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\\'","").split(","))
    }

    List<String>getLinks (Instance instance){
        return Arrays.asList("")
    }

    Map<String,Map<String,String>> getPortBindings(Instance instance){
        //m sure there is a fancier way to do this using getPortMappingsFromInstance()
      instance.portMapping.collectEntries {k,v->
            return [k+"/tcp",Arrays.asList(Collections.unmodifiableMap("HostPort":v.toString()))]
        } as Map<String, Map<String, String>>

    }


}


