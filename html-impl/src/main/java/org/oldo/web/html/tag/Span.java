package org.oldo.web.html.tag;

import org.oldo.web.html.Tag;
import org.oldo.web.html.marker.BodyContent;
import org.oldo.web.html.marker.SpanAttribute;
import org.oldo.web.html.marker.SpanContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.web.html.Element.span;

/**
 * The 'span' tag
 */
public class Span<M> extends Tag<M, SpanAttribute<M>, SpanContent<M>>
    implements BodyContent<M> {

    @SafeVarargs
    public static <M> Span<M> span(SpanContent<M>... contents) {
        return new Span<>(emptyList(), asList(contents));
    }

    public Span(Iterable<SpanAttribute<M>> attributes,
                Iterable<SpanContent<M>> contents) {
        super(span, attributes, contents);
    }
}
