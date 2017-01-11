package org.kaddiya.grorchestrator.serialiser

import org.kaddiya.grorchestrator.models.core.latest.GrorProject

/**
 * Created by Webonise on 11/01/17.
 */
public interface GrorProjectSerialiser {

    public void serialiseProjectToFile(Object project,String fileName);
}
