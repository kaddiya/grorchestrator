package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import com.google.inject.Key
import com.google.inject.assistedinject.FactoryModuleBuilder
import com.google.inject.name.Names
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.factory.DockerContainerActionFactory
import org.kaddiya.grorchestrator.guice.factory.DockerRemoteAPIActionAggregatorFactory
import org.kaddiya.grorchestrator.guice.factory.DockerhubAuthCredetialsBuilderFactory
import org.kaddiya.grorchestrator.guice.factory.InstanceListerFactory
import org.kaddiya.grorchestrator.managers.impl.*
import org.kaddiya.grorchestrator.managers.impl.aggregators.RedeployContainerImpl
import org.kaddiya.grorchestrator.managers.impl.monitoringactions.InstanceListerImpl
import org.kaddiya.grorchestrator.managers.interfaces.DockerRemoteInterface
import org.kaddiya.grorchestrator.managers.interfaces.aggregators.DockerRemoteAPIActionsAggregator
import org.kaddiya.grorchestrator.managers.interfaces.monitoringactions.InstancesLister

/**
 * Created by Webonise on 01/07/16.
 */
@CompileStatic
class DockerRemoteAPIModule extends AbstractModule {

    @Override
    protected void configure() {

        this.install(new FactoryModuleBuilder()
                .implement(InstancesLister.class, InstanceListerImpl.class)
                .build(InstanceListerFactory))
        this.install(new FactoryModuleBuilder()
                .build(DockerhubAuthCredetialsBuilderFactory))

        this.install(new FactoryModuleBuilder()
                .implement(Key.get(DockerRemoteInterface.class, Names.named("Puller")), PullImageImpl.class)
                .implement(Key.get(DockerRemoteInterface.class, Names.named("Creator")), CreateContainerImpl.class)
                .implement(Key.get(DockerRemoteInterface.class, Names.named("Runner")), RunContainerImpl.class)
                .implement(Key.get(DockerRemoteInterface.class, Names.named("Killer")), KillContainerImpl.class)
                .implement(Key.get(DockerRemoteInterface.class, Names.named("Remover")), RemoveContainerImpl.class)
                .implement(Key.get(DockerRemoteInterface.class, Names.named("Inspector")), InspectContainerImpl.class)

                .build(DockerContainerActionFactory.class))

        this.install(new FactoryModuleBuilder()
                .implement(Key.get(DockerRemoteAPIActionsAggregator.class,Names.named("Redeployer")),RedeployContainerImpl.class)
                .build(DockerRemoteAPIActionAggregatorFactory.class)
        )
    }
}
