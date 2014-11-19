package org.guppy4j.web.html.attribute;

/**
 * Base class for all attributes (typed name/value pairs)
 */
public class AttributeBase<V> {

    private final String name;
    private V value;

    protected AttributeBase(String name, V value) {
        this.name = name;
        this.value = value;
    }

    public final String name() {
        return name;
    }

    public final V value() {
        return value;
    }
}
