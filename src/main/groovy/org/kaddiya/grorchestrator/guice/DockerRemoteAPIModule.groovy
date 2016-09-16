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
                .implement(PullImage.class, PullImageImpl.class)
                .build(PullImageFactory.class));
        this.install(new FactoryModuleBuilder()
                .implement(CreateContainer.class, CreateContainerImpl.class)
                .build(CreateContainerFactory))
        this.install(new FactoryModuleBuilder()
                .implement(RunContainer.class, RunContainerImpl.class)
                .build(RunContainerFactory))
        this.install(new FactoryModuleBuilder()
                .implement(KillContainer.class, KillContainerImpl.class)
                .build(KillContainerFactory))
        this.install(new FactoryModuleBuilder()
                .implement(DockerContainerRemoveManager.class, DockerContainerRemoveManagerImpl.class)
                .build(DockerContainerRemoveMangerFactory))
        this.install(new FactoryModuleBuilder()
                .implement(DockerRemoteAPIInfoManager.class, DockerRemoteAPIInfoManagerImpl.class)
                .build(DockerRemoteAPIInfoManagerFactory))

    }
}
