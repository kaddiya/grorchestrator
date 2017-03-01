package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import org.kaddiya.grorchestrator.helpers.DockerContainerCreationRequestBuilder
import org.kaddiya.grorchestrator.helpers.HostConfigBuilder
import org.kaddiya.grorchestrator.helpers.impl.DockerContainerCreationRequestBuilderImpl
import org.kaddiya.grorchestrator.helpers.impl.DockerhubAuthCredentialsBuilder
import org.kaddiya.grorchestrator.helpers.impl.HostConfigBuilderImpl

/**
 * Created by Webonise on 01/07/16.
 */
class HelperModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DockerhubAuthCredentialsBuilder)
        bind(DockerContainerCreationRequestBuilder).to(DockerContainerCreationRequestBuilderImpl)
        bind(HostConfigBuilder).to(HostConfigBuilderImpl)
    }

}
