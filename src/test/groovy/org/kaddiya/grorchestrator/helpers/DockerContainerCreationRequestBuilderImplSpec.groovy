package org.kaddiya.grorchestrator.helpers

import groovy.json.JsonOutput
import org.kaddiya.grorchestrator.helpers.impl.DockerContainerCreationRequestBuilderImpl
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 13/07/16.
 */
class DockerContainerCreationRequestBuilderImplSpec extends Specification {


    @Shared
    DockerContainerCreationRequestBuilder fixture = new DockerContainerCreationRequestBuilderImpl()

    def "getPortMappingsFromInstance should return a proper port mapping as expected by the Docker remote api V 1.19"() {
        given:
        Instance instance = getDummyInstanceForVOnePointNineteen()
        when:
        Map<String, Object> result = fixture.getPortMappingsFromInstance(instance)
        then:

        assert "{\"22/tcp\":{}}" == (JsonOutput.toJson(result))
    }

    def "getVolumes should return a proper volume mapping as expected by the Docker remote api V 1.19"() {
        given:
        Instance instance = getDummyInstanceForVOnePointNineteen()
        when:
        Map<String, Object> result = fixture.getVolumes(instance)
        println(result.toMapString())
        then:
        assert "{\"/data-1\":{},\"/data-2\":{}}" == (JsonOutput.toJson(result))
    }

    def "getEnvironmentMappings should return a proper list of environment variables and values as expected by the Docker remote api V1.19"() {
        given:
        Instance instance = getDummyInstanceForVOnePointNineteen()
        when:
        List<String> result = (fixture as DockerContainerCreationRequestBuilderImpl).getEnvironmentMappings(instance)
        then:
        assert "[env=test, number=3]" == (result.toListString())
    }

    def "getCommandToBeExecuted should return a an array of strings that is expected by the remote API"() {
        given:
        Instance instance = getDummyInstanceForVOnePointTwentyFour()
        when:
        List<String> result = (fixture as DockerContainerCreationRequestBuilderImpl).getCommandToBeExecuted(instance.commandToBeExecuted)
        List<String> expected = Arrays.asList("someCommand", "arg1", "arg2", "--switch1", "switch1Args")
        then:
        assert expected == result
    }

    Instance getDummyInstanceForVOnePointNineteen() {
        return new Instance("redis.proof.com", "redis", "latest",
                "redis-vm-1",
                Collections.unmodifiableMap(["/home/deploy/cache-data-1": "/data-1", "/home/deploy/cache-data-2": "/data-2"]), Collections.unmodifiableMap([10222: 22]) as Map<Integer, Integer>,
                Collections.unmodifiableMap([:]),
                Collections.unmodifiableMap(["env": "test", "number": "3"]
                ))
    }

    Instance getDummyInstanceForVOnePointTwentyFour() {
        return new Instance("redis.proof.com", "redis", "latest",
                "redis-vm-1",
                Collections.unmodifiableMap(["/home/deploy/cache-data-1": "/data-1", "/home/deploy/cache-data-2": "/data-2"]), Collections.unmodifiableMap([10222: 22]) as Map<Integer, Integer>,
                Collections.unmodifiableMap([:]),
                Collections.unmodifiableMap(["env": "test", "number": "3"]),
                Collections.unmodifiableMap(["name.for.container.1": "alias.for.container.1"]),
                Collections.unmodifiableList(["container_1", "container_2"]),
                "someCommand arg1 arg2 --switch1 switch1Args"
        )
    }

}
