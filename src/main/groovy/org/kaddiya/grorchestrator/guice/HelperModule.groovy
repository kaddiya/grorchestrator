package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import org.kaddiya.grorchestrator.helpers.*
import org.kaddiya.grorchestrator.helpers.impl.*
import org.kaddiya.grorchestrator.updators.PreviousToLatestSchemaUpdator
import org.kaddiya.grorchestrator.updators.impl.PreviousToLatestSchemaUpdatorImpl

/**
 * Created by Webonise on 01/07/16.
 */
class HelperModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DockerhubAuthCredentialsBuilder)
        bind(InstanceFinder).to(InstanceFinderImpl)
        bind(DockerContainerCreationRequestBuilder).to(DockerContainerCreationRequestBuilderImpl)
        bind(HostConfigBuilder).to(HostConfigBuilderImpl)
        bind(EnvironmentVarsResolver)
        bind(PreviousToLatestSchemaUpdator).to(PreviousToLatestSchemaUpdatorImpl)
        bind(HostFinderImpl)
    }

}
