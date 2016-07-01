package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.factory.DockerImagePullManagerFactory
import org.kaddiya.grorchestrator.managers.DockerImagePullManager
import org.kaddiya.grorchestrator.managers.impl.DockerImagePullManagerImpl

/**
 * Created by Webonise on 01/07/16.
 */
@CompileStatic
class DockerRemoteAPIModule extends  AbstractModule {

    @Override
    protected void configure() {
        this.install(new FactoryModuleBuilder()
                .implement(DockerImagePullManager.class, DockerImagePullManagerImpl.class)
                .build(DockerImagePullManagerFactory.class));
    }
}
