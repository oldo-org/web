package org.guppy4j.web.html.attribute;

import org.guppy4j.web.html.render.Renderer;

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
    public void render(Renderer r, M m) {
        r.render(' ');
        r.render(name);
        r.render('=');
        r.render('"');
        r.render(value(m));
        r.render('"');
    }

    private String value(M model) {
        final Object result = value.apply(model);
        return result == null ? "" : result.toString();
    }
}
