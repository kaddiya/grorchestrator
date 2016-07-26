package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.DockerRemoteAPIInfoManager;
import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 22/07/16.
 */
public interface DockerRemoteAPIInfoManagerFactory {

    public DockerRemoteAPIInfoManager create(Instance instance);
}

