package org.kaddiya.grorchestrator.models

import groovy.transform.Canonical
import groovy.transform.Immutable

/**
 * Created by Webonise on 19/03/16.
 */
@Canonical
class Component {
    String name
    List<Instance> instances
}
