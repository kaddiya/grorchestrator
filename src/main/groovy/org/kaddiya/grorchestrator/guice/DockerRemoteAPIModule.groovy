package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.factory.*
import org.kaddiya.grorchestrator.managers.*
import org.kaddiya.grorchestrator.managers.impl.*

/**
 * Created by Webonise on 01/07/16.
 */
@CompileStatic
class DockerRemoteAPIModule extends AbstractModule {

    @Override
    protected void configure() {
        this.install(new FactoryModuleBuilder()
                .implement(DockerImagePullManager.class, DockerImagePullManagerImpl.class)
                .build(DockerImagePullManagerFactory.class));
        this.install(new FactoryModuleBuilder()
                .implement(DockerContainerCreator.class, DockerContainerCreatorImpl.class)
                .build(DockerContainerCreatorFactory))
        this.install(new FactoryModuleBuilder()
                .implement(DockerContainerRunnerManager.class, DockerContainerRunnerManagerImpl.class)
                .build(DockerContainerRunnerFactory))
        this.install(new FactoryModuleBuilder()
                .implement(DockerContainerKillManager.class, DockerContainerKillManagerImpl.class)
                .build(DockerContainerKillManagerFactory))
        this.install(new FactoryModuleBuilder()
                .implement(DockerContainerRemoveManager.class, DockerContainerRemoveManagerImpl.class)
                .build(DockerContainerRemoveMangerFactory))
        this.install(new FactoryModuleBuilder()
                        .implement(DockerRemoteAPIInfoManager.class, DockerRemoteAPIInfoManagerImpl.class)
                        .build(DockerRemoteAPIInfoManagerFactory))

    }
}
