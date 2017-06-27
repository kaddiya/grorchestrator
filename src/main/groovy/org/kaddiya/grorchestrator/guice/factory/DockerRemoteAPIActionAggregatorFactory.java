package org.kaddiya.grorchestrator.guice.factory;

import com.google.inject.name.Named;
import org.kaddiya.grorchestrator.managers.interfaces.aggregators.DockerRemoteAPIActionsAggregator;
import org.kaddiya.grorchestrator.models.core.latest.Host;
import org.kaddiya.grorchestrator.models.core.latest.Instance;

/**
 * Created by Webonise on 27/06/17.
 */
public interface DockerRemoteAPIActionAggregatorFactory {

    @Named("Redeployer")
    public DockerRemoteAPIActionsAggregator getContainerRedeployer(Instance instance, Host host);
}
