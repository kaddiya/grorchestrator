package org.kaddiya.grorchestrator.managers.impl.aggregators

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.guice.factory.DockerContainerActionFactory
import org.kaddiya.grorchestrator.managers.interfaces.DockerRemoteAPISequentialAggregator
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance

@CompileStatic
public class RedeployContainerImpl extends DockerRemoteAPISequentialAggregator {


    @Inject
    public RedeployContainerImpl(
            @Assisted Instance instance, @Assisted Host host, DockerContainerActionFactory factory) {
        super(factory.getContainerKiller(instance, host), factory.getContainerRunner(instance, host));
    }

}
