package org.kaddiya.grorchestrator.updators;

import org.kaddiya.grorchestrator.models.core.latest.Component;
import org.kaddiya.grorchestrator.models.core.latest.GrorProject;
import org.kaddiya.grorchestrator.models.core.latest.Host;
import org.kaddiya.grorchestrator.models.core.latest.Instance;

import java.util.List;

/**
 * Created by Webonise on 11/01/17.
 */
public interface PreviousToLatestSchemaUpdator {

    public GrorProject updateFromPreviousProject(org.kaddiya.grorchestrator.models.core.previous.GrorProject previousProject);

    public List<Host> getLatestHostListFromPreviousProject(org.kaddiya.grorchestrator.models.core.previous.GrorProject previousProject);

    public List<Instance> getLatestInstancesListFromPreviousComponents(org.kaddiya.grorchestrator.models.core.previous.Component previousComponent);

    public List<Component> getLatestComponentsListFromPreviousGrorProject(org.kaddiya.grorchestrator.models.core.previous.GrorProject previousProject);
}
