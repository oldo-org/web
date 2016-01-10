package org.guppy4j.html.tag;

import org.guppy4j.html.Tag;
import org.guppy4j.html.marker.AnyContent;
import org.guppy4j.html.marker.DivAttribute;
import org.guppy4j.html.marker.DivContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.guppy4j.html.Element.div;

/**
 * The 'div' tag
 */
public class Div<M> extends Tag<M, DivAttribute<M>, DivContent<M>>
    implements AnyContent<M> {

    @SafeVarargs
    public static <M> Div<M> div(DivContent<M>... contents) {
        return new Div<>(emptyList(), asList(contents));
    }

    public Div(Iterable<DivAttribute<M>> attributes,
                Iterable<DivContent<M>> contents) {
        super(div, attributes, contents);
    }
}
