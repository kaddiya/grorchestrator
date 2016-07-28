package org.kaddiya.grorchestrator.models.remotedocker.responses.info

import groovy.transform.Canonical

/**
 * Created by Webonise on 28/07/16.
 */
@Canonical
class DockerContainerInspectionResponse {
    String HostnamePath
    Config Config
    String RestartCount
    String ResolvConfPath
    DockerContainerResponseHostConfig HostConfig
    Map<String, String> Volumes //needed for 1.9
    Map<String, Boolean> VolumesRW  //needed for 1.9

    GraphDriver GraphDriver  //added in 1.12
    String Path
    String ProcessLabel
    String Driver
    String Name
    String[] Args
    String Created
    State State
    String LogPath
    String AppArmorProfile
    String ExecDriver
    String Image
    String ExecIDs
    String Id
    String MountLabel
    NetworkSettings NetworkSettings
    String HostsPath
    List<Mount> Mounts

}
