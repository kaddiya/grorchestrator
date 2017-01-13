package org.kaddiya.grorchestrator.managers.impl.monitoringactions

import com.google.inject.Inject
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.helpers.impl.HostFinderImpl
import org.kaddiya.grorchestrator.managers.interfaces.monitoringactions.InstancesLister
import org.kaddiya.grorchestrator.models.core.latest.Component
import org.kaddiya.grorchestrator.models.core.latest.GrorProject
import org.kaddiya.grorchestrator.models.core.latest.Host
import org.kaddiya.grorchestrator.models.core.latest.Instance
import org.kaddiya.grorchestrator.models.monitoringactions.InstanceSummary

/**
 * Created by Webonise on 13/01/17.
 */
@CompileStatic
class InstanceListerImpl implements InstancesLister {

    @Inject
    HostFinderImpl hostFinder

    @Override
    List<InstanceSummary> getSummaryOfAllInstances(GrorProject project) {
        List<InstanceSummary> result = project.components.collectNested { Component c ->
            c.instances.collect { Instance it ->
                Host host = hostFinder.getHostToInteractWith(project, it.hostId)
                new InstanceSummary(it.name, it.hostId, host.getIp(), it.imageName)
            }
        }
        return result.flatten() as ArrayList<InstanceSummary>
    }
}
