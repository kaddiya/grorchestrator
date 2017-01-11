package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import org.kaddiya.grorchestrator.deserialisers.GrorProjectDeserialiser
import org.kaddiya.grorchestrator.deserialisers.previous.GrorProjectDeserialiserImpl

/**
 * Created by Webonise on 01/07/16.
 */
class DeserialiserModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(GrorProjectDeserialiser).to(GrorProjectDeserialiserImpl)
    }
}
