package org.kaddiya.grorchestrator.serialisers;

import org.kaddiya.grorchestrator.models.core.GrorProject;

import java.io.File;

/**
 * Created by Webonise on 24/06/16.
 */
public interface GrorProjectSerialiser {

    public GrorProject constructGrorProject(File name);
}
