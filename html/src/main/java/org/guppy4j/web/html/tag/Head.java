package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.marker.HeadAttribute;
import org.guppy4j.web.html.marker.HeadContent;
import org.guppy4j.web.html.marker.HtmlContent;

import static org.guppy4j.web.html.tag.Element.head;

/**
 * "head" tag
 */
public class Head<M> extends Tag<M, HeadAttribute<M>, HeadContent<M>> implements HtmlContent<M> {

    public Head(Iterable<HeadAttribute<M>> attributes,
                Iterable<HeadContent<M>> contents) {
        super(head, attributes, contents);
    }
}
