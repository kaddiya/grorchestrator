package org.kaddiya.grorchestrator.models.remotedocker.responses.info

import com.sun.org.apache.xpath.internal.operations.Bool

/**
 * Created by Webonise on 28/07/16.
 */
class Config {
    String User

    Object OnBuild

    Boolean Tty

    String NetworkDisabled

    Boolean StdinOnce

    Object Labels

    Map<String,Object> ExposedPorts

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

    Object PortSpecs

    String Hostname

    Map<String,Object> Volumes

    Boolean OpenStdin
}
