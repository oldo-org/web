package org.guppy4j.html.logic;

import org.guppy4j.html.Out;
import org.guppy4j.html.Renderable;
import org.guppy4j.html.marker.AnyContent;

import java.util.function.Function;

/**
 * Switches the model to a property of the surrounding model
 */
public final class ForProperty<M, X> implements AnyContent<M> {

    private final Function<M, X> getter;
    private final Renderable<X>[] contents;

    public ForProperty(Function<M, X> getter, Renderable<X>... contents) {
        this.getter = getter;
        this.contents = contents;
    }

    @Override
    public void render(Out out, M model) {
        final X x = getter.apply(model);
        for (Renderable<X> content : contents) {
            content.render(out, x);
        }
    }
}
