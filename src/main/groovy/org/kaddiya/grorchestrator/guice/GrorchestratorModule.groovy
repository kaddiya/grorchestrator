package org.kaddiya.grorchestrator.guice

import com.google.inject.AbstractModule
import com.google.inject.Module

/**
 * Created by Webonise on 01/07/16.
 */
class GrorchestratorModule extends AbstractModule {

    private Module[] modules

    GrorchestratorModule() {
    }

    GrorchestratorModule(Module ... modules) {
        super()
        this.modules = modules
    }


    @Override
    protected void configure() {
        def grorModule = this
        this.modules.each {module -> grorModule.install(module)}
    }
}
