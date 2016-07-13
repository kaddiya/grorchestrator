package org.kaddiya.grorchestrator.helpers

import groovy.json.JsonOutput
import net.sf.json.util.JSONBuilder
import org.kaddiya.grorchestrator.helpers.impl.DockerContainerCreationRequestBuilderImpl
import org.kaddiya.grorchestrator.models.core.Host
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.HostConfig
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
        assert "{\"6379/tcp\":{}}" == (JsonOutput.toJson(result))
    }

    def "getMountBindings should return a proper volume mapping as expected by the Docker remote api"(){
        given:
        Instance instance = getDummyInstance()
        when:
        Map<String,Object>result = fixture.getMountBindings(instance)
        println(result.toMapString())
        then:
        assert "{\"/home/deploy/cache-data-1\":{},\"/home/deploy/cache-data-2\":{}}" == (JsonOutput.toJson(result))
    }

    def "getHostConfig should return a proper volume mapping as expected by the Docker remote api"(){
        given:
        Instance instance = getDummyInstance()
        when:
        HostConfig result = fixture.getHostConfig(instance)
        then:
        assert result.binds.size() == 2

        assert result.binds.get(0) == "/home/deploy/cache-data-1:/data"
    }


    Instance getDummyInstance(){
        return new Instance("redis.proof.com", "redis", "latest",
                new Host("127.0.0.1", "redis-vm-1", 2376),
                Collections.unmodifiableMap(["/home/deploy/cache-data-1": "/data","/home/deploy/cache-data-2": "/data"]), Collections.unmodifiableMap([6379: 6379]) as Map<Integer, Integer>,
                Collections.unmodifiableMap([:])
        )
    }

}
