package org.kaddiya.grorchestrator.managers

import org.kaddiya.grorchestrator.models.core.Instance

/**
 * Created by Webonise on 24/06/16.
 */
interface DockerImagePullManager {

    String constructURL(Instance instance)
}