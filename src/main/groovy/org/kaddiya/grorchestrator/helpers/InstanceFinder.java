package org.kaddiya.grorchestrator.helpers;

import org.kaddiya.grorchestrator.models.core.GrorProject;
import org.kaddiya.grorchestrator.models.core.Instance;

/**
 * Created by Webonise on 02/07/16.
 */
public interface InstanceFinder {

    Instance getInstanceToInteractWith(GrorProject project,String instanceName);
}
