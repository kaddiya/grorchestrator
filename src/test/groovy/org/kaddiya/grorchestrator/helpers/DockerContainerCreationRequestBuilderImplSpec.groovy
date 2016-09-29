package org.kaddiya.grorchestrator.helpers

import groovy.json.JsonOutput
import org.kaddiya.grorchestrator.helpers.impl.DockerContainerCreationRequestBuilderImpl
import org.kaddiya.grorchestrator.models.core.Host
import org.kaddiya.grorchestrator.models.core.Instance
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


    Instance getDummyInstanceForVOnePointNineteen() {
        return new Instance("redis.proof.com", "redis", "latest",
                new Host("127.0.0.1", "redis-vm-1", 2376, "http", "1.7.1.", "1.19"),
                Collections.unmodifiableMap(["/home/deploy/cache-data-1": "/data-1", "/home/deploy/cache-data-2": "/data-2"]), Collections.unmodifiableMap([10222: 22]) as Map<Integer, Integer>,
                Collections.unmodifiableMap([:]),
                Collections.unmodifiableMap(["env": "test", "number": "3"]
                ))
    }

    Instance getDummyInstanceForVOnePointTwentyFour() {
        return new Instance("redis.proof.com", "redis", "latest",
                new Host("127.0.0.1", "redis-vm-1", 2376, "http", "1.12.", "1.24"),
                Collections.unmodifiableMap(["/home/deploy/cache-data-1": "/data-1", "/home/deploy/cache-data-2": "/data-2"]), Collections.unmodifiableMap([10222: 22]) as Map<Integer, Integer>,
                Collections.unmodifiableMap([:]),
                Collections.unmodifiableMap(["env": "test", "number": "3"]
                ))
    }

}
