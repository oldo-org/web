package org.guppy4j.html.content.type;

import org.guppy4j.html.marker.HeadContent;

/**
 * A content type
 *
 * @see <a href="http://www.w3.org/TR/html5/dom.html#metadata-content">HTML5 spec</a>
 */
public interface Metadata<M> extends HeadContent<M> {

/*
Metadata content is content that sets up the presentation or behavior
of the rest of the content, or that sets up the relationship of the document
with other documents, or that conveys other "out of band" information.

    base
    link
    meta
    noscript
    script
    style
    template
    title

 */
}


