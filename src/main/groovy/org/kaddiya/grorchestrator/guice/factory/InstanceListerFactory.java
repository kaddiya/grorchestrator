package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.interfaces.monitoringactions.InstancesLister;
import org.kaddiya.grorchestrator.models.core.latest.GrorProject;

/**
 * Created by Webonise on 13/01/17.
 */
public interface InstanceListerFactory {

    public InstancesLister create();
}
