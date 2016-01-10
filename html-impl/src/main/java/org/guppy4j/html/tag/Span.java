package org.guppy4j.html.tag;

import org.guppy4j.html.Tag;
import org.guppy4j.html.marker.BodyContent;
import org.guppy4j.html.marker.SpanAttribute;
import org.guppy4j.html.marker.SpanContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.guppy4j.html.Element.span;

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
