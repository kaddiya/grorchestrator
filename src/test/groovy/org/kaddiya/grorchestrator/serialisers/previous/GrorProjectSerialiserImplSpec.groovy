package org.kaddiya.grorchestrator.serialisers.previous

import groovy.util.logging.Log
import org.kaddiya.grorchestrator.models.core.DockerHubAuth
import org.kaddiya.grorchestrator.models.core.SystemInfo
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.previous.Component
import org.kaddiya.grorchestrator.models.core.previous.GrorProject
import org.kaddiya.grorchestrator.models.core.previous.Instance
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

    def "constructGrorProject should return a properly constructed object for GrorProject with full gror.json"() {
        given:
        URL url = getClass().getClassLoader().getResource("gror.json")
        File file = new File(url.toURI())
        when:
        GrorProject actualProject = serialiser.constructGrorProject(file)
        GrorProject expectedProject = getDummyFullGrorProject()
        then:
        assert actualProject: " project cant be null"
        assert actualProject == expectedProject: "Something went wrong in the parsing"


    }

    def "constructGrorProject should return a properly constructed object for gror_without_creds.json"() {
        given:
        URL url = getClass().getClassLoader().getResource("gror_without_creds.json")
        File file = new File(url.toURI())
        when:
        GrorProject actualProject = serialiser.constructGrorProject(file)
        GrorProject expectedProject = getDummyEmptyCredsGrorProject()
        then:
        assert actualProject: " project cant be null"
        assert actualProject == expectedProject: "Something went wrong in the parsing"

    }

    def cleanup() {
        System.setProperty("registry_username", "")
        System.setProperty("registry_password", "")
        System.setProperty("registry_auth", "")
        System.setProperty("registry_email", "")
    }

    def getDummyFullGrorProject() {
        SystemInfo info = new SystemInfo("demo","0.0.2")
        DockerHubAuth dockerHubAuth = new DockerHubAuth("username", "password", "auth", "email@example.com")

        List<Component> components = Arrays.asList(

                new Component("api-server", Arrays.asList(
                        new Instance("api.project.com",
                                "dockerhubid/image_name",
                                "latest",
                                new Host("127.0.0.1", "api-vm-1", 2376, "http", "1.7.1", "1.19","/path/to/certs"),
                                Collections.unmodifiableMap(["/home/ubuntu/api-data/some-api-data": "/root/some-api-data",
                                                             "/home/ubuntu/api-data/logs"           : "/opt/appData/api-logs/logs/"]),
                                Collections.unmodifiableMap([8080: 8080]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap(["db.project.com": "127.0.0.1", "redis.project.com": "127.0.0.1"]),
                                Collections.unmodifiableMap(["key-1": "value-1", "key-2": "value-2", "key-3": "value-3"]),
                                Collections.unmodifiableMap(["name.for.container.1": "alias.for.container.1"]),
                               Collections.unmodifiableList(["container_1","container_2"]),
                                "someCommand arg1 arg2 --switch1 switchArg1"

                        )

                ))
        )
        GrorProject project = new GrorProject(info, components)
        return project
    }

    def getDummyEmptyCredsGrorProject() {
        SystemInfo info = new SystemInfo("demo")
        List<Host> hosts = Arrays.asList(
                new Host("127.0.0.1", "redis-vm-1", 2376, "http", "1.7.1", "1.19"),
                new Host("127.0.0.1", "mysql-vm-1", 2376, "http", "1.7.1", "1.19"),
                new Host("127.0.0.1", "api-vm-1", 2376, "http", "1.7.1", "1.19"),
                new Host("127.0.0.1", "frontend-vm-1", 2376, "http", "1.7.1", "1.19")
        )
        List<Component> components = Arrays.asList(

                new Component("api-server", Arrays.asList(
                        new Instance("api.project.com", "dockerhubid/image_name", "latest",
                                new Host("127.0.0.1", "api-vm-1", 2376, "http", "1.7.1", "1.19"),
                                Collections.unmodifiableMap(["/home/ubuntu/api-data/some-api-data": "/root/some-api-data",
                                                             "/home/ubuntu/api-data/logs"           : "/opt/appData/api-logs/logs/"]), Collections.unmodifiableMap([8080: 8080]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap(["db.project.com": "127.0.0.1", "redis.project.com": "127.0.0.1"],

                                ),
                                Collections.unmodifiableMap(["key-1": "value-1", "key-2": "value-2", "key-3": "value-3"]),
                                Collections.unmodifiableMap(["name.for.container.1": "alias.for.container.1"])
                        )

                ))
        )
        GrorProject project = new GrorProject(info, components)
        return project
    }
}

