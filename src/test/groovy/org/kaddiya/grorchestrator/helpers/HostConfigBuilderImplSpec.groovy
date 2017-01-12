package org.kaddiya.grorchestrator.helpers

import groovy.json.JsonOutput
import org.kaddiya.grorchestrator.helpers.impl.HostConfigBuilderImpl
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 13/07/16.
 */
class HostConfigBuilderImplSpec extends Specification {

    @Shared
    HostConfigBuilder fixture = new HostConfigBuilderImpl()

    def "getBinds should return the proper object of Binds of HostConfig as expected by the Docker remote api"() {
        given:
        Instance instance = getDummyInstance()
        when:
        List<String> result = (fixture as HostConfigBuilderImpl).getBinds(instance)
        then:
        assert result.size() == 2
        assert result.get(0) == "1:2"
    }

    def "getPortBindings should return a proper volume mapping as expected by the Docker remote api"() {
        given:
        Instance instance = getDummyInstance()
        when:
        Map<String, Map<String, String>> result = (fixture as HostConfigBuilderImpl).getPortBindings(instance)
        println(JsonOutput.toJson(result))
        then:
        assert "{\"22/tcp\":[{\"HostPort\":\"11022\"}]}" == JsonOutput.toJson(result)
    }

    def "getExtraHostsMapping should return a proper volume mapping as expected by the Docker remote api"() {
        given:
        Instance instance = getDummyInstance()
        when:
        List<String> result = (fixture as HostConfigBuilderImpl).getExtraHostsMapping(instance)

        then:
        assert result.size() == 2
        assert result.get(0) == "some.hostname-1.on.container:ip.for.host-1"
        assert result.get(1) == "some.hostname-2.on.container:ip.for.host-2"
    }

    def "getLinks should return a proper list of strings for the links as expected by the Docker remote api"() {
        given:
        Instance instance = getDummyInstance()
        when:
        List<String> result = (fixture as HostConfigBuilderImpl).getLinks(instance)

        then:
        assert result.size() == 2
        assert result.get(0) == "container-1:alias-for-container-1"
        assert result.get(1) == "container-2:alias-for-container-2"
    }


    Instance getDummyInstance() {
        return new Instance("redis.proof.com", "redis", "latest",
                "redis-vm-1",
                Collections.unmodifiableMap(["1": "2", "3": "4"]), Collections.unmodifiableMap([11022: 22]) as Map<Integer, Integer>,
                Collections.unmodifiableMap(["some.hostname-1.on.container": "ip.for.host-1", "some.hostname-2.on.container": "ip.for.host-2"]),
                Collections.unmodifiableMap(["": ""]),
                Collections.unmodifiableMap(["container-1": "alias-for-container-1", "container-2": "alias-for-container-2"])

        )
    }
}
