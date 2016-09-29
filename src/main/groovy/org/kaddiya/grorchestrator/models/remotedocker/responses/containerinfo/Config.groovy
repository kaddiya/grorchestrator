package org.kaddiya.grorchestrator.models.remotedocker.responses.containerinfo

import groovy.transform.Canonical

/**
 * Created by Webonise on 28/07/16.
 */
@Canonical
class Config {
    String User

    String OnBuild

    Boolean Tty

    String NetworkDisabled

    Boolean StdinOnce

    String Labels

    Map<String, String> ExposedPorts

    List<String> Cmd

    String WorkingDir

    List<String> Env

    String[] Entrypoint

    String MacAddress

    Boolean AttachStdout

    String Domainname

    Boolean AttachStderr

    String VolumeDriver

    String Image

    Boolean AttachStdin

    String PortSpecs

    String Hostname

    Map<String, String> Volumes

    Boolean OpenStdin
}
