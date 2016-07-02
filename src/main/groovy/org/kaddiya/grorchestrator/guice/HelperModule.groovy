package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import org.kaddiya.grorchestrator.helpers.DockerAuthCredentialsBuilder
import org.kaddiya.grorchestrator.helpers.InstanceFinder
import org.kaddiya.grorchestrator.helpers.InstanceFinderImpl

/**
 * Created by Webonise on 01/07/16.
 */
class HelperModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DockerAuthCredentialsBuilder)
        bind(InstanceFinder).to(InstanceFinderImpl)
    }
}
