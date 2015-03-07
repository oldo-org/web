package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.marker.BodyContent;
import org.guppy4j.web.html.marker.SpanAttribute;
import org.guppy4j.web.html.marker.SpanContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.guppy4j.web.html.tag.Element.span;

/**
 * TODO: Document this!
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
