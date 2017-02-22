package org.kaddiya.grorchestrator.models.core.latest

import groovy.transform.Canonical

/**
 * Created by Webonise on 11/01/17.
 */
@Canonical
class Component {
    String name
    List<Instance> instances
}
