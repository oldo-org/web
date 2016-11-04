package org.guppy4j.html.attribute;

import org.guppy4j.html.Out;

import java.util.function.Function;

/**
 * Base class for all attributes (typed name/value pairs)
 */
public class AttributeBase<M> implements Attribute<M> {

    private final String name;
    private final Function<M, ?> value;

    AttributeBase(String name, Function<M, ?> value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void render(Out out, M m) {
        out.write(' ');
        out.write(name);
        out.write('=');
        out.write('"');
        out.write(value(m));
        out.write('"');
    }

    private String value(M model) {
        final Object result = value.apply(model);
        return result == null ? "" : result.toString();
    }
}
