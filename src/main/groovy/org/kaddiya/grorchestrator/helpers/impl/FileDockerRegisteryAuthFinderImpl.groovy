package org.kaddiya.grorchestrator.helpers.impl

import com.google.inject.Inject

import org.kaddiya.grorchestrator.deserialisers.latest.GrorProjectDeserialiserImpl
import org.kaddiya.grorchestrator.helpers.DockerRegistryAuthFinder
import org.kaddiya.grorchestrator.models.core.DockerHubAuth
import org.kaddiya.grorchestrator.models.core.latest.GrorProject

/**
 * Created by Webonise on 01/03/17.
 */
class FileDockerRegisteryAuthFinderImpl implements DockerRegistryAuthFinder {

    @Inject
    GrorProjectDeserialiserImpl deserialiser

    public final static String ACTUAL_GROR_FILE_NAME = "v1.0.0_gror.json"

    @Override
    DockerHubAuth getDockerHubAuthForId(String id) {

        GrorProject project = deserialiser.constructGrorProject(new File(ACTUAL_GROR_FILE_NAME))
        assert project && project.authData
        DockerHubAuth auth = project.authData.find { DockerHubAuth auth ->
            return auth.key == id
        }
    }
}
