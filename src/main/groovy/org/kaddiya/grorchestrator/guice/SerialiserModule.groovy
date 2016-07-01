package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import org.kaddiya.grorchestrator.serialisers.GrorProjectSerialiser
import org.kaddiya.grorchestrator.serialisers.GrorProjectSerialiserImpl

/**
 * Created by Webonise on 01/07/16.
 */
class SerialiserModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(GrorProjectSerialiser).to(GrorProjectSerialiserImpl)
    }
}
