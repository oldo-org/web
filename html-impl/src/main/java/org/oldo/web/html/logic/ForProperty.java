package org.oldo.web.html.logic;

import org.oldo.web.html.Out;
import org.oldo.web.html.Renderable;
import org.oldo.web.html.marker.AnyContent;

import java.util.function.Function;

/**
 * Switches the model to a property of the surrounding model
 */
public final class ForProperty<M, X> implements AnyContent<M> {

    private final Function<M, X> getter;
    private final Renderable<X>[] contents;

    @SafeVarargs
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
