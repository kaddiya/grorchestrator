package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.helpers.impl.DockerhubAuthCredentialsBuilder;
import org.kaddiya.grorchestrator.models.core.DockerHubAuth;

/**
 * Created by Webonise on 13/01/17.
 */
public interface DockerhubAuthCredetialsBuilderFactory {

    public DockerhubAuthCredentialsBuilder create(DockerHubAuth authObject);
}

