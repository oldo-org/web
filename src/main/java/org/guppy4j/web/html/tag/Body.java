package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.marker.BodyAttribute;
import org.guppy4j.web.html.marker.BodyContent;
import org.guppy4j.web.html.marker.HtmlContent;

import static org.guppy4j.web.html.Element.body;

/**
 * "body" tag
 */
public class Body extends Tag<BodyAttribute<?>, BodyContent> implements HtmlContent {

    public Body(Iterable<BodyAttribute<?>> attributes, Iterable<BodyContent> contents) {
        super(body, attributes, contents);
    }
}
