package org.guppy4j.web.html;

/**
 * Typed name/value pair
 */
public interface Attribute<V> {

    String name();

    V value();
}
