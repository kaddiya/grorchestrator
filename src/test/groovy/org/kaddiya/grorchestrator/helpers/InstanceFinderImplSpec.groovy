package org.kaddiya.grorchestrator.helpers

import org.kaddiya.grorchestrator.helpers.impl.InstanceFinderImpl
import org.kaddiya.grorchestrator.models.core.*
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 04/07/16.
 */
class InstanceFinderImplSpec extends Specification {

    @Shared
    InstanceFinder fixture = new InstanceFinderImpl()

    def "getInstanceToInteractWith should throw an IAE when it encounters no matching instances"() {
        given:
        GrorProject project = getDummyGrorProject()
        String instanceName = "non-existing-instance"
        when:
        fixture.getInstanceToInteractWith(project, instanceName)
        then:
        IllegalArgumentException iae = thrown()
        iae.message.contains("No instances with $instanceName detected")

    }

    def "getInstanceToInteractWith should throw an IAE when it encounters multiple matching instances"() {
        given:
        GrorProject project = getDummyGrorProjectWithDuplicateInstances()
        String instanceName = "redis.proof.com"
        when:
        fixture.getInstanceToInteractWith(project, instanceName)
        then:
        IllegalArgumentException iae = thrown()
        iae.message.contains("Multiple instances with $instanceName detected")

    }

    def getDummyGrorProject() {
        SystemInfo info = new SystemInfo("demo")
        List<Host> hosts = Arrays.asList(
                new Host("127.0.0.1", "redis-vm-1", 2376),
                new Host("127.0.0.1", "mysql-vm-1", 2376),
                new Host("127.0.0.1", "api-vm-1", 2376),
                new Host("127.0.0.1", "frontend-vm-1", 2376)
        )
        List<Component> components = Arrays.asList(
                new Component("redis-cache", Arrays.asList(
                        new Instance("redis.proof.com", "redis", "latest",
                                new Host("127.0.0.1", "redis-vm-1", 2376),
                                Collections.unmodifiableMap(["/home/deploy/cache-data": "/data"]), Collections.unmodifiableMap([6379: 6379]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap([:])
                        )

                ))
        )
        GrorProject project = new GrorProject(info, components)
        return project
    }

    def getDummyGrorProjectWithDuplicateInstances() {
        SystemInfo info = new SystemInfo("demo")
        List<Host> hosts = Arrays.asList(
                new Host("127.0.0.1", "redis-vm-1", 2376),
                new Host("127.0.0.1", "mysql-vm-1", 2376),
                new Host("127.0.0.1", "api-vm-1", 2376),
                new Host("127.0.0.1", "frontend-vm-1", 2376)
        )
        List<Component> components = Arrays.asList(
                new Component("redis-cache", Arrays.asList(
                        new Instance("redis.proof.com", "redis", "latest",
                                new Host("127.0.0.1", "redis-vm-1", 2376),
                                Collections.unmodifiableMap(["/home/deploy/cache-data": "/data"]), Collections.unmodifiableMap([6379: 6379]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap([:])
                        ),
                        new Instance("redis.proof.com", "redis", "latest",
                                new Host("127.0.0.1", "redis-vm-1", 2376),
                                Collections.unmodifiableMap(["/home/deploy/cache-data": "/data"]), Collections.unmodifiableMap([6379: 6379]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap([:])
                        )

                ))
        )
        GrorProject project = new GrorProject(info, components)
        return project
    }
}
