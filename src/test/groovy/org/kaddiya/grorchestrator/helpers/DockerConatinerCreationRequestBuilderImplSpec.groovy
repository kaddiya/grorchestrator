package org.kaddiya.grorchestrator.helpers

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

    def "getPortMappingsFromInstance should return a proper port mapping as expected by the Docker remote api"(){
        given:
        Instance instance = getDummyInstance()
        when:
        Map<String,Object>result = fixture.getPortMappingsFromInstance(instance)
        then:
        assert  true
    }

    Instance getDummyInstance(){
        return new Instance("redis.proof.com", "redis", "latest",
                new Host("127.0.0.1", "redis-vm-1", 2376),
                Collections.unmodifiableMap(["/home/deploy/cache-data": "/data"]), Collections.unmodifiableMap([6379: 6379]) as Map<Integer, Integer>,
                Collections.unmodifiableMap([:])
        )
    }

}
