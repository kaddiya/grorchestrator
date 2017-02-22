package org.kaddiya.grorchestrator.helpers.impl

import org.kaddiya.grorchestrator.models.core.latest.GrorProject
import org.kaddiya.grorchestrator.models.core.latest.Host

/**
 * Created by Webonise on 12/01/17.
 */
class HostFinderImpl {

    public Host getHostToInteractWith(GrorProject project, String hostId) {

        Host host = project.hosts.find { it ->
            it.alias == hostId
        }

        if (!host) {
            throw new IllegalStateException("In the current project no host with alias $hostId is referenced.Are you sure you configured the instances properly?")
        }

        return host

    }
}
