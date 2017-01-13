package org.kaddiya.grorchestrator.managers.interfaces.monitoringactions;

import org.kaddiya.grorchestrator.models.core.latest.GrorProject;
import org.kaddiya.grorchestrator.models.monitoringactions.InstanceSummary;

import java.util.List;

/**
 * Created by Webonise on 13/01/17.
 */
public interface InstancesLister {
    List<InstanceSummary> getSummaryOfAllInstances(GrorProject project);
}
