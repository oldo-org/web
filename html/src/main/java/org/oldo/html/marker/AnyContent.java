package org.oldo.html.marker;

/**
 * Extends all content interfaces
 */
public interface AnyContent<M> extends
    HtmlContent<M>,
    HeadContent<M>,
    TitleContent<M>,
    BodyContent<M>,
    SpanContent<M> {
}
