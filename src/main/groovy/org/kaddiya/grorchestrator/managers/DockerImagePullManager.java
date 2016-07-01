package org.kaddiya.grorchestrator.managers;

import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 29/06/16.
 */
public interface DockerImagePullManager {

    public String pullImage(String imageName,String tag);
}
