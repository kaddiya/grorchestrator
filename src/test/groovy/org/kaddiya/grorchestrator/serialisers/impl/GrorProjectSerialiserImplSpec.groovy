package org.kaddiya.grorchestrator.serialisers.impl

import groovy.util.logging.Log
import org.kaddiya.grorchestrator.models.core.Component
import org.kaddiya.grorchestrator.models.core.GrorProject
import org.kaddiya.grorchestrator.models.core.Host
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.core.SystemInfo
import org.kaddiya.grorchestrator.serialisers.GrorProjectSerialiser
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 19/03/16.
 */
@Log
class GrorProjectSerialiserImplSpec extends Specification {

    @Shared
    GrorProjectSerialiser serialiser = new GrorProjectSerialiserImpl();

    def "constructGrorProject should thrown an exception on recieving a null file name"() {
        when:
        serialiser.constructGrorProject(null)
        then:
        IllegalStateException e = thrown()
        assert e.message.contains("Filename cant be null")
    }

    def "constructGrorProject should throw an exception when a non existing file is recieved"() {
        given:
        String fileName = "nonexistingfile.json"
        when:
        serialiser.constructGrorProject(new File(fileName))
        then:
        IllegalStateException e = thrown()
        assert e.message.contains("File $fileName doesnt exist")
    }

    def "constructGrorProject should return a properly constructed object for GrorProject"() {
        given:
        URL url = getClass().getClassLoader().getResource("gror.json")
        File file = new File(url.toURI())
        when:
        GrorProject actualProject = serialiser.constructGrorProject(file)
        GrorProject expectedProject = getDummyGrorProject()
        then:
        assert actualProject: " project cant be null"
        assert actualProject == expectedProject: "Something went wrong in the parsing"


    }

    def getDummyGrorProject() {
        SystemInfo info = new SystemInfo("demo")
        List<Host> hosts = Arrays.asList(
                new Host("52.23.179.239", "redis-vm-1", 2376),
                new Host("52.23.179.239", "mysql-vm-1", 2376),
                new Host("52.23.179.239", "api-vm-1", 2376),
                new Host("52.23.179.239", "frontend-vm-1", 2376)
        )
        List<Component> components = Arrays.asList(
                new Component("redis-cache", Arrays.asList(
                        new Instance("redis.proof.com", "redis", "latest",
                                new Host("52.23.179.239", "redis-vm-1", 2376),
                                Collections.unmodifiableMap(["/home/deploy/cache-data": "/data"]), Collections.unmodifiableMap([6379: 6379]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap([:])
                        )

                )),
                new Component("mysql-db", Arrays.asList(
                        new Instance("db.proof.com", "proofadmin/proof-mysql-db", "staging-2",
                                new Host("52.23.179.239", "mysql-vm-1", 2376),
                                Collections.unmodifiableMap(["/home/deploy/db-data/data-files": "/var/lib/mysql"]), Collections.unmodifiableMap([3306: 3306]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap([:])
                        )

                )),
                new Component("api-server", Arrays.asList(
                        new Instance("api.proof.com", "proofadmin/proof-service-api", "latest",
                                new Host("52.23.179.239", "api-vm-1", 2376),
                                Collections.unmodifiableMap(["/home/ubuntu/api-data/proof-data-sets": "/root/Proof-Data-Sets",
                                                             "/home/ubuntu/api-data/logs"           : "/opt/appData/proof-service-api/logs/"]), Collections.unmodifiableMap([8080: 8080]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap(["db.proof.com": "52.23.179.239", "redis.proof.com": "52.23.179.239"])
                        )

                )),
                new Component("frontend", Arrays.asList(
                        new Instance("static.proof.com", "proofadmin/proof-service-api", "latest",
                                new Host("52.23.179.239", "static-vm-1", 2376),
                                Collections.unmodifiableMap([:]), Collections.unmodifiableMap([80: 80]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap(["api.proof.com": "52.23.179.239"])
                        )

                ))
        )
        GrorProject project = new GrorProject(info, components)
        return project
    }
}

