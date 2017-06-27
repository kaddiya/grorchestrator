package org.kaddiya.grorchestrator.managers.interfaces.monitoringactions;

import org.kaddiya.grorchestrator.models.core.latest.GrorProject;
import org.kaddiya.grorchestrator.models.monitoringactions.InstanceSummary;

import java.util.List;


public interface InstancesLister {
    List<InstanceSummary> getSummaryOfAllInstances(GrorProject project);
}
