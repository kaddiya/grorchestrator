package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.DockerImagePullManager;
import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 01/07/16.
 */
public interface DockerImagePullManagerFactory {

    public DockerImagePullManager create(Instance instance);

}
