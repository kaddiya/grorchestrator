package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import com.google.inject.Key
import com.google.inject.assistedinject.FactoryModuleBuilder
import com.google.inject.name.Names
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.factory.*
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.managers.impl.*
import org.kaddiya.grorchestrator.managers.impl.monitoringactions.InstanceListerImpl
import org.kaddiya.grorchestrator.managers.interfaces.DockerRemoteInterface
import org.kaddiya.grorchestrator.managers.interfaces.monitoringactions.InstancesLister

/**
 * Created by Webonise on 01/07/16.
 */
@CompileStatic
class DockerRemoteAPIModule extends AbstractModule {

    @Override
    protected void configure() {
        this.install(new FactoryModuleBuilder()
                .implement(DockerRemoteAPI.class, PullImageImpl.class)
                .build(PullImageFactory.class));
        this.install(new FactoryModuleBuilder()
                .implement(DockerRemoteAPI.class, CreateContainerImpl.class)
                .build(CreateContainerFactory))
        this.install(new FactoryModuleBuilder()
                .implement(DockerRemoteAPI.class, RunContainerImpl.class)
                .build(RunContainerFactory))
        this.install(new FactoryModuleBuilder()
                .implement(DockerRemoteAPI.class, KillContainerImpl.class)
                .build(KillContainerFactory))
        this.install(new FactoryModuleBuilder()
                .implement(DockerRemoteAPI.class, RemoveContainerImpl.class)
                .build(RemoveContainerFactory))

        this.install(new FactoryModuleBuilder()
                .implement(DockerRemoteAPI.class, InspectContainerImpl.class)
                .build(InspectContainerFactory))

        this.install(new FactoryModuleBuilder()
                .implement(InstancesLister.class, InstanceListerImpl.class)
                .build(InstanceListerFactory))
        this.install(new FactoryModuleBuilder()
                .build(DockerhubAuthCredetialsBuilderFactory))

        this.install(new FactoryModuleBuilder()
                .implement(Key.get(DockerRemoteInterface.class, Names.named("PullImage")), PullImageImpl.class)
                .build(NamedDockerRemoteApiFactory.class));

    }
}
