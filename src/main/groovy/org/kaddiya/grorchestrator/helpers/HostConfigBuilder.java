package org.kaddiya.grorchestrator.helpers;

import org.kaddiya.grorchestrator.models.core.latest.Instance;
import org.kaddiya.grorchestrator.models.remotedocker.requests.HostConfig;

/**
 * Created by Webonise on 13/07/16.
 */
public interface HostConfigBuilder {

    public HostConfig constructHostConfig(Instance instance);
}
