package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.interfaces.PullImage;
import org.kaddiya.grorchestrator.models.core.previous.Instance;

/**
 * Created by Webonise on 01/07/16.
 */
public interface PullImageFactory {

    public PullImage create(Instance instance);

}
