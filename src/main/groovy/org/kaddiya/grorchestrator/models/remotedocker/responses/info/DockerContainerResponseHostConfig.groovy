package org.kaddiya.grorchestrator.models.remotedocker.responses.info

import groovy.transform.Canonical
import groovy.transform.InheritConstructors
import org.kaddiya.grorchestrator.models.remotedocker.requests.HostConfig

/**
 * Created by Webonise on 28/07/16.
 */

@Canonical
class DockerContainerResponseHostConfig extends HostConfig {

    String ContainerIDFile

    String MemorySwap

    String Privileged

    String BlkioWeight

    Object Devices

    String CpuShares

    List<String> Binds //extend from hostConfig and then remove this property

    String CpusetMems

    Object VolumesFrom

    LogConfig LogConfig

    String PidMode

    String CpuQuota

    List<String> LxcConf

    String Memory

    List<String> Dns

    String CgroupParent

    String PublishAllPorts

    List<String> ExtraHosts  //extend from hostConfig and then remove this property

    Map<String, Map<String, String>> PortBindings //extend from hostConfig and then remove this property

    String CpusetCpus

    String CpuPeriod

    RestartPolicy RestartPolicy

    String OomKillDisable

    List<String> Ulimits

    String ReadonlyRootfs

    String NetworkMode

    List<String> CapAdd

    List<String> CapDrop

    List<String> Links //extend from hostconfig and then remove this property

    List<String> DnsSearch

    List<String> SecurityOpt

    String UTSMode

    String IpcMode

    //not found in 1.9
    Boolean AutoRemove
    String VolumeDriver
    List<String> DnsOptions
    String GroupAdd
    String Cgroup
    Integer OomScoreAdj
    Object StorageOpt
    String UsernsMode
    BigInteger ShmSize
    List<Integer> ConsoleSize
    String Isolation
    Object BlkioWeightDevice
    Object BlkioDeviceReadBps
    Object BlkioDeviceWriteBps
    Object BlkioDeviceReadIOps
    Object BlkioDeviceWriteIOps
    Integer DiskQuota
    Integer KernelMemory
    Integer MemoryReservation
    Integer MemorySwappiness
    Integer PidsLimit
    Integer CpuCount
    Integer CpuPercent
    Integer BlkioIOps
    Integer BlkioBps
    Integer SandboxSize
}
