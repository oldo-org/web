package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.marker.BodyContent;
import org.guppy4j.web.html.marker.HeadContent;
import org.guppy4j.web.html.marker.HtmlAttribute;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Tag creation using method names
 * that match the HTML5 tag names
 */
public class TagFactory {
    public static Html html(List<? extends HtmlAttribute> attributes, Head head, Body body) {
        return new Html(attributes, head, body);
    }

    public static Head head(HeadContent... contents) {
        return new Head(null, asList(contents));
    }

    public static Body body(BodyContent... contents) {
        return new Body(null, asList(contents));
    }
}
