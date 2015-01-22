package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.marker.BodyContent;
import org.guppy4j.web.html.marker.SpanAttribute;
import org.guppy4j.web.html.marker.SpanContent;

import static org.guppy4j.web.html.tag.Element.span;

/**
 * TODO: Document this!
 */
public class Span<M> extends Tag<M, SpanAttribute<M>, SpanContent<M>>
    implements BodyContent<M> {

    public Span(Iterable<SpanAttribute<M>> attributes,
                Iterable<SpanContent<M>> contents) {
        super(span, attributes, contents);
    }
}
