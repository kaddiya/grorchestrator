package org.kaddiya.grorchestrator.models.remotedocker.responses.info

import groovy.transform.Canonical

/**
 * Created by Webonise on 28/07/16.
 */
@Canonical
class State {

    String Status // added in 1.12

    String ExitCode

    String Restarting

    String Paused

    String Running

    String Dead

    String Pid

    String FinishedAt

    String StartedAt

    String Error

    String OOMKilled
}
