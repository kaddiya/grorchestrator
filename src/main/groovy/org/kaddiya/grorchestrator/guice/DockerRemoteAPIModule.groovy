package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.factory.DockerContainerCreatorFactory
import org.kaddiya.grorchestrator.guice.factory.DockerContainerRunnerFactory
import org.kaddiya.grorchestrator.guice.factory.DockerImagePullManagerFactory
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerContainerRunnerManager
import org.kaddiya.grorchestrator.managers.DockerImagePullManager
import org.kaddiya.grorchestrator.managers.impl.DockerContainerCreatorImpl
import org.kaddiya.grorchestrator.managers.impl.DockerContainerRunnerManagerImpl
import org.kaddiya.grorchestrator.managers.impl.DockerImagePullManagerImpl

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
    }
}
