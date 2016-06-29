package org.kaddiya.grorchestrator.managers

import org.kaddiya.grorchestrator.managers.impl.DockerImagePullManagerImpl
import org.kaddiya.grorchestrator.models.core.Host
import org.kaddiya.grorchestrator.models.core.Instance
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 24/06/16.
 */
class DockerImagePullManagerImplSpec extends Specification {

    @Shared
    DockerRemoteApiManager imagePullManager


    def "constructUrl should return a proper URL for pulling an image"(){
        given:
        Instance instance = new Instance("sample-instance-1","repo/sample-service","sample-tag",
                new Host("127.0.0.1","localhost",2376),
                [:],[:],[:])

        when:
        imagePullManager = new DockerImagePullManagerImpl(instance)

        then:
        assert  imagePullManager.apiUrl == "http://127.0.0.1:2376/images/create?fromImage=repo/sample-service:sample-tag"
    }


    def "pullImage should pull the image"(){
        given:
        Instance instance = new Instance("sample-instance-1","proofadmin/proof-static-frontend","testing-34",
                new Host("test-proof.weboapps.com","test-proof.weboapps.com",2376),
                [:],[:],[:])

        imagePullManager = new DockerImagePullManagerImpl(instance)
        when:
        (imagePullManager as DockerImagePullManager).pullImage()
        then:
        assert  true


    }
}
