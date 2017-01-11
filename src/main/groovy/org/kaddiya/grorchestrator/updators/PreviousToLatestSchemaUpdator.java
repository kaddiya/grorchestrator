package org.kaddiya.grorchestrator.updators;

import org.kaddiya.grorchestrator.models.core.latest.GrorProject;

/**
 * Created by Webonise on 11/01/17.
 */
public interface PreviousToLatestSchemaUpdator {

    public GrorProject updateFromPreviousProject(org.kaddiya.grorchestrator.models.core.previous.GrorProject previousProject);
}
