package org.oldo.web.html.logic;

import org.oldo.web.html.Content;
import org.oldo.web.html.Renderable;
import org.oldo.web.html.marker.AnyContent;
import org.oldo.web.html.model.Variable;

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
