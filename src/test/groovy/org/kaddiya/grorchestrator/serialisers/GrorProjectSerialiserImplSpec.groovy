package org.kaddiya.grorchestrator.serialisers

import groovy.util.logging.Log
import org.kaddiya.grorchestrator.GrorProjectSerialiserImpl
import org.kaddiya.grorchestrator.models.core.Component
import org.kaddiya.grorchestrator.models.core.DockerHubAuth
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
    def "prepareEnvironmentVariables should set proper environment variables through the gror.json"(){
        given:
        URL url = getClass().getClassLoader().getResource("gror.json")
        File file = new File(url.toURI())
        GrorProject project = serialiser.constructGrorProject(file)
        when:
        serialiser.prepareEnvironmentVariables(project)
        then:
        assert System.getProperty("registry.username") == "username"
        assert System.getProperty("registry.email") == "email@example.com"
        assert System.getProperty("registry.password") == "password"
        assert System.getProperty("registry.auth")  == "auth"

    }

    def "prepareEnvironmentVariables should have the proper environment variables set sourcing from a well formed file"(){
        given:
        URL url = getClass().getClassLoader().getResource("gror.json")
        File file = new File(url.toURI())
        GrorProject project = serialiser.constructGrorProject(file)
        when:
        serialiser.prepareEnvironmentVariables(project)
        then:
        assert System.getProperty("registry.username") == "username"
        assert System.getProperty("registry.email") == "email@example.com"
        assert System.getProperty("registry.password") == "password"
        assert System.getProperty("registry.auth")  == "auth"

    }

    def "prepareEnvironmentVariables should throw an IAE while having sourcing from a file having the  credentials and not having the env values set"(){
        given:
        URL url = getClass().getClassLoader().getResource("gror_without_creds.json")
        File file = new File(url.toURI())
        GrorProject project = serialiser.constructGrorProject(file)
        when:
        serialiser.prepareEnvironmentVariables(project)
        then:
        IllegalArgumentException iae = thrown()
    }

    def "prepareEnvironmentVariables should set the proper environment variables after reading from a file not having the credentials and having the env values set"(){
        given:
        URL url = getClass().getClassLoader().getResource("gror_without_creds.json")
        File file = new File(url.toURI())
        GrorProject project = serialiser.constructGrorProject(file)
        System.setProperty("registry.username","username")
        System.setProperty("registry.password","password")
        System.setProperty("registry.auth","auth")
        System.setProperty("registry.email","email@example.com")
        when:
        serialiser.prepareEnvironmentVariables(project)
        then:
        assert  System.getProperty("registry.username") == "username" : "username not properly set"
        assert  System.getProperty("registry.password") == "password" :"password not prperly set"
        assert  System.getProperty("registry.auth") == "auth"  : "auth not properly set"
        assert  System.getProperty("registry.email") == "email@example.com" :"email not properly set"
    }

    def "prepareEnvironmentVariables should override the proper environment variables after reading from a well formed gror.json and having the env values set"(){
        given:
        URL url = getClass().getClassLoader().getResource("gror.json")
        File file = new File(url.toURI())
        GrorProject project = serialiser.constructGrorProject(file)
        System.setProperty("registry.username","overidden-username")
        System.setProperty("registry.passowrd","overidden-password")
        System.setProperty("registry.auth","overidden-auth")
        System.setProperty("registry.email","overidden-email@example.com")
        when:
        serialiser.prepareEnvironmentVariables(project)
        then:
        assert  System.getProperty("registry.username") == "overidden-username" : "username not properly overriden"
        assert  System.getProperty("registry.passowrd") == "overidden-password" :"password not properly overriden"
        assert  System.getProperty("registry.auth") == "overidden-auth"  : "auth not properly overriden"
        assert  System.getProperty("registry.email") == "overidden-email@example.com" :"email not properly overriden"
    }

    def getDummyFullGrorProject() {
        SystemInfo info = new SystemInfo("demo")
        DockerHubAuth  dockerHubAuth = new DockerHubAuth("username","password","auth","email@example.com")
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

                )),
                new Component("mysql-db", Arrays.asList(
                        new Instance("db.proof.com", "proofadmin/proof-mysql-db", "staging-2",
                                new Host("127.0.0.1", "mysql-vm-1", 2376),
                                Collections.unmodifiableMap(["/home/deploy/db-data/data-files": "/var/lib/mysql"]), Collections.unmodifiableMap([3306: 3306]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap([:])
                        )

                )),
                new Component("api-server", Arrays.asList(
                        new Instance("api.proof.com", "proofadmin/proof-service-api", "latest",
                                new Host("127.0.0.1", "api-vm-1", 2376),
                                Collections.unmodifiableMap(["/home/ubuntu/api-data/proof-data-sets": "/root/Proof-Data-Sets",
                                                             "/home/ubuntu/api-data/logs"           : "/opt/appData/proof-service-api/logs/"]), Collections.unmodifiableMap([8080: 8080]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap(["db.proof.com": "127.0.0.1", "redis.proof.com": "127.0.0.1"])
                        )

                )),
                new Component("frontend", Arrays.asList(
                        new Instance("static.proof.com", "proofadmin/proof-service-api", "latest",
                                new Host("127.0.0.1", "static-vm-1", 2376),
                                Collections.unmodifiableMap([:]), Collections.unmodifiableMap([80: 80]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap(["api.proof.com": "127.0.0.1"])
                        )

                ))
        )
        GrorProject project = new GrorProject(info,dockerHubAuth,components)
        return project
    }

    def getDummyEmptyCredsGrorProject() {
        SystemInfo info = new SystemInfo("demo")
        DockerHubAuth  dockerHubAuth = new DockerHubAuth()
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

                )),
                new Component("mysql-db", Arrays.asList(
                        new Instance("db.proof.com", "proofadmin/proof-mysql-db", "staging-2",
                                new Host("127.0.0.1", "mysql-vm-1", 2376),
                                Collections.unmodifiableMap(["/home/deploy/db-data/data-files": "/var/lib/mysql"]), Collections.unmodifiableMap([3306: 3306]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap([:])
                        )

                )),
                new Component("api-server", Arrays.asList(
                        new Instance("api.proof.com", "proofadmin/proof-service-api", "latest",
                                new Host("127.0.0.1", "api-vm-1", 2376),
                                Collections.unmodifiableMap(["/home/ubuntu/api-data/proof-data-sets": "/root/Proof-Data-Sets",
                                                             "/home/ubuntu/api-data/logs"           : "/opt/appData/proof-service-api/logs/"]), Collections.unmodifiableMap([8080: 8080]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap(["db.proof.com": "127.0.0.1", "redis.proof.com": "127.0.0.1"])
                        )

                )),
                new Component("frontend", Arrays.asList(
                        new Instance("static.proof.com", "proofadmin/proof-service-api", "latest",
                                new Host("127.0.0.1", "static-vm-1", 2376),
                                Collections.unmodifiableMap([:]), Collections.unmodifiableMap([80: 80]) as Map<Integer, Integer>,
                                Collections.unmodifiableMap(["api.proof.com": "127.0.0.1"])
                        )

                ))
        )
        GrorProject project = new GrorProject(info,dockerHubAuth,components)
        return project
    }
}

