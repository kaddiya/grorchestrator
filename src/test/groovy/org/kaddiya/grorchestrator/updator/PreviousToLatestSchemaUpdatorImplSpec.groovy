package org.kaddiya.grorchestrator.updator

import org.kaddiya.grorchestrator.deserialisers.previous.GrorProjectDeserialiserImpl
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.core.previous.GrorProject
import org.kaddiya.grorchestrator.updators.PreviousToLatestSchemaUpdator
import org.kaddiya.grorchestrator.updators.impl.PreviousToLatestSchemaUpdatorImpl
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 11/01/17.
 */
class PreviousToLatestSchemaUpdatorImplSpec extends Specification {

    @Shared
    URL previousGrorJsonUrl = getClass().getClassLoader().getResource("gror.json")
    @Shared
    File previousGrorJsonFile = new File(previousGrorJsonUrl.toURI())
    @Shared
    GrorProjectDeserialiserImpl previousProjectSerializer = new GrorProjectDeserialiserImpl()
    @Shared
    GrorProject previousProject = previousProjectSerializer.constructGrorProject(previousGrorJsonFile)
    @Shared
    PreviousToLatestSchemaUpdator updatorImpl = new PreviousToLatestSchemaUpdatorImpl()

    def "updateFromPreviousProject should update properly to the new Version"() {

        when:
        org.kaddiya.grorchestrator.models.core.latest.GrorProject updatedProject = updatorImpl.updateFromPreviousProject(previousProject)

        then:
        assert updatedProject instanceof org.kaddiya.grorchestrator.models.core.latest.GrorProject
    }

    def "getLatestHostListFromPreviousProject should return a proper list of the host from the previous GrorProject"() {
        when:
        List<Host> newHostList = updatorImpl.getLatestHostListFromPreviousProject(previousProject)
        then:
        assert newHostList.size() == 1
        Host host = newHostList.get(0)
        assert host.equals(new Host("127.0.0.1", "api-vm-1", 2376, "http", "1.7.1", "1.19", "/path/to/certs"))
    }

    def "getLatestInstancesListFromPreviousComponents should return a proper list of instances for a particular component in the new form from the previous Components"() {
        when:
        List<Instance> newInstanceList = updatorImpl.getLatestInstancesListFromPreviousComponents(previousProject.components.get(0))
        then:
        assert newInstanceList
        assert newInstanceList.size() == 1
        assert newInstanceList.get(0).equals(new Instance("api.project.com",
                "dockerhubid/image_name",
                "latest",
                "api-vm-1",
                Collections.unmodifiableMap(["/home/ubuntu/api-data/some-api-data": "/root/some-api-data",
                                             "/home/ubuntu/api-data/logs"         : "/opt/appData/api-logs/logs/"]),
                Collections.unmodifiableMap([8080: 8080]) as Map<Integer, Integer>,
                Collections.unmodifiableMap(["db.project.com": "127.0.0.1", "redis.project.com": "127.0.0.1"]),
                Collections.unmodifiableMap(["key-1": "value-1", "key-2": "value-2", "key-3": "value-3"]),
                Collections.unmodifiableMap(["name.for.container.1": "alias.for.container.1"]),
                Collections.unmodifiableList(["container_1", "container_2"]),
                "someCommand arg1 arg2 --switch1 switchArg1"

        ))
    }

}

