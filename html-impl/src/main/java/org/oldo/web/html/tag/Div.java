package org.oldo.web.html.tag;

import org.oldo.web.html.Tag;
import org.oldo.web.html.marker.AnyContent;
import org.oldo.web.html.marker.DivAttribute;
import org.oldo.web.html.marker.DivContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.web.html.Element.div;

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
