package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import org.kaddiya.grorchestrator.helpers.*
import org.kaddiya.grorchestrator.helpers.impl.DockerContainerCreationRequestBuilderImpl
import org.kaddiya.grorchestrator.helpers.impl.HostConfigBuilderImpl
import org.kaddiya.grorchestrator.helpers.impl.InstanceFinderImpl

/**
 * Created by Webonise on 01/07/16.
 */
class HelperModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DockerAuthCredentialsBuilder)
        bind(InstanceFinder).to(InstanceFinderImpl)
        bind(DockerContainerCreationRequestBuilder).to(DockerContainerCreationRequestBuilderImpl)
        bind(HostConfigBuilder).to(HostConfigBuilderImpl)
        bind(EnvironmentVarsResolver)
    }
}
