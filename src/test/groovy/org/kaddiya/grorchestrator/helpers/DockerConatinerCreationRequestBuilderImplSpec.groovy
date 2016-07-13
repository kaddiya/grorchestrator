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
class DockerConatinerCreationRequestBuilderImplSpec extends Specification {


    @Shared
    DockerContainerCreationRequestBuilder fixture = new DockerContainerCreationRequestBuilderImpl()

    def "getPortMappingsFromInstance should return a proper port mapping as expected by the Docker remote api"() {
        given:
        Instance instance = getDummyInstance()
        when:
        Map<String, Object> result = fixture.getPortMappingsFromInstance(instance)
        then:
        assert "{\"6379/tcp\":{}}" == (JsonOutput.toJson(result))
    }

    def "getVolumes should return a proper volume mapping as expected by the Docker remote api"() {
        given:
        Instance instance = getDummyInstance()
        when:
        Map<String, Object> result = fixture.getVolumes(instance)
        println(result.toMapString())
        then:
        assert "{\"/home/deploy/cache-data-1\":{},\"/home/deploy/cache-data-2\":{}}" == (JsonOutput.toJson(result))
    }


    Instance getDummyInstance() {
        return new Instance("redis.proof.com", "redis", "latest",
                new Host("127.0.0.1", "redis-vm-1", 2376),
                Collections.unmodifiableMap(["/home/deploy/cache-data-1": "/data", "/home/deploy/cache-data-2": "/data"]), Collections.unmodifiableMap([22: 11022]) as Map<Integer, Integer>,
                Collections.unmodifiableMap([:])
        )
    }

}
