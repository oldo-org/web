package org.guppy4j.html.logic;

import org.guppy4j.html.Content;
import org.guppy4j.html.Renderable;
import org.guppy4j.html.marker.AnyContent;
import org.guppy4j.html.model.Variable;

import java.util.function.Function;

/**
 * Provides logic functions
 */
public class LogicFactory {

    @SafeVarargs
    public static <M, I, CM extends Content<M>> CM forEach(
        Function<M, Iterable<I>> items,
        Function<M, Variable<I>> holder,
        CM... contents) {

        final ForEach<M, I, CM> forEach = new ForEach<>(items, holder, contents);
        return (CM) forEach;
    }

    @SafeVarargs
    public static <M, X> AnyContent<M> forProperty(
            Function<M, X> getter,
            Renderable<X>... contents) {
        return new ForProperty<>(getter, contents);
    }
}
