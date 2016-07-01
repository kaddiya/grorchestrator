package org.kaddiya.grorchestrator.managers;

/**
 * Created by Webonise on 29/06/16.
 */
public interface DockerImagePullManager {

    public String pullImage(String imageName,String tag);
}
