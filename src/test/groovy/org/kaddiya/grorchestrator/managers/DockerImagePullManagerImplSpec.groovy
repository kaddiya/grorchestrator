package org.kaddiya.grorchestrator.managers

import org.kaddiya.grorchestrator.managers.impl.PullImageImpl
import org.kaddiya.grorchestrator.managers.interfaces.PullImage
import org.kaddiya.grorchestrator.models.core.Host
import org.kaddiya.grorchestrator.models.core.Instance
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 24/06/16.
 */
class DockerImagePullManagerImplSpec extends Specification {

    @Shared
    PullImage imagePullManager

    @Ignore
    def "pullImage should pull the image"() {
        given:
        Instance instance = new Instance("sample-instance-1", "sample-repo-name/sample-image-name", "sample-tag",
                new Host("127.0.0.1", "127.0.0.1", 2376),
                [:], [:], [:])

        imagePullManager = new PullImageImpl(instance)
        imagePullManager.client.post >> {
            return "Pulling from sample-repo-name/sample-image-name"
        }
        when:
        String response = imagePullManager.pullImage()
        then:
        assert response.contains("Pulling from sample-repo-name/sample-image-name")


    }

}
