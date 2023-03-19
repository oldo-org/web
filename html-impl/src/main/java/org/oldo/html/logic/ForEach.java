package org.oldo.html.logic;

import org.oldo.html.Content;
import org.oldo.html.marker.AnyContent;
import org.oldo.html.model.Variable;
import org.oldo.html.Out;

import java.util.function.Function;

/**
 * Content iterator
 */
public class ForEach<M, I, CM extends Content<M>> implements AnyContent<M> {

    private final Function<M, Iterable<I>> items;
    private final Function<M, Variable<I>> holder;
    private final CM[] contents;

    @SafeVarargs
    public ForEach(Function<M, Iterable<I>> items,
                   Function<M, Variable<I>> holder,
                   CM... contents) {
        this.items = items;
        this.holder = holder;
        this.contents = contents;
    }

    @Override
    public void render(Out out, M model) {
        final Variable<I> var = holder.apply(model);
        for (I item : items.apply(model)) {
            var.set(item);
            for (CM content : contents) {
                content.render(out, model);
            }
        }
        // clear the loop variable
        var.set(null);
    }
}
