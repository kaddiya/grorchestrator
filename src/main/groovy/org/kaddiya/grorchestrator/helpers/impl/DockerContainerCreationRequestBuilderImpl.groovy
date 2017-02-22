package org.kaddiya.grorchestrator.helpers.impl

import com.google.inject.Inject
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.helpers.DockerContainerCreationRequestBuilder
import org.kaddiya.grorchestrator.helpers.HostConfigBuilder
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.DockerContainerCreationRequest

/**
 * Created by Webonise on 13/07/16.
 */
@CompileStatic
class DockerContainerCreationRequestBuilderImpl implements DockerContainerCreationRequestBuilder {


    @Inject
    HostConfigBuilder hostConfigBuilderImpl

    @Override
    DockerContainerCreationRequest getContainerCreationRequest(Instance instance) {
        DockerContainerCreationRequest request = new DockerContainerCreationRequest()
       // request.cmd = getCommandToBeExecuted(instance.commandToBeExecuted)
        request.image = instance.imageName + ":" + instance.tag
        //request.exposedPorts = getPortMappingsFromInstance(instance)
        //request.volumes = getVolumes(instance)
        //request.env = getEnvironmentMappings(instance)
        //request.hostConfig = hostConfigBuilderImpl.constructHostConfig(instance)
        //request.volumesFrom = instance.volumesFrom
        return request
    }

    @Override
    Map<String, Object> getPortMappingsFromInstance(Instance instance) {
        Map<String, Object> result = instance.portMapping.collectEntries { k, v ->
            return [v + "/tcp", new Object()]
        } as Map<String, Object>
        return result
    }

    @Override
    Map<String, Object> getVolumes(Instance instance) {
        Map<String, Object> result = instance.volumeMapping.collectEntries { k, v ->
            return [v, new Object()]
        } as Map<String, Object>
        return result
    }

    List<String> getEnvironmentMappings(Instance instance) {
        if (instance.envMap) {
            List<String> result = instance.envMap.collect { k, v -> k + "=" + v }
            return result
        } else {
            return Arrays.asList("")
        }
    }

    List<String> getCommandToBeExecuted(String command) {
        if (command) {
            return Arrays.asList(command.split(" "))
        } else return null;

    }

}


