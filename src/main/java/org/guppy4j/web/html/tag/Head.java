package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.marker.HeadAttribute;
import org.guppy4j.web.html.marker.HeadContent;
import org.guppy4j.web.html.marker.HtmlContent;

import static org.guppy4j.web.html.Element.head;

/**
 * "head" tag
 */
public class Head extends Tag<HeadAttribute<?>, HeadContent> implements HtmlContent {

    public Head(Iterable<HeadAttribute<?>> attributes, Iterable<HeadContent> contents) {
        super(head, attributes, contents);
    }
}
