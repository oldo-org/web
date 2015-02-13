package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.marker.HtmlAttribute;
import org.guppy4j.web.html.marker.HtmlContent;

import java.util.List;

import static java.util.Arrays.asList;
import static org.guppy4j.web.html.tag.Element.html;

/**
 * "html" tag
 */
public class Html<M> extends Tag<M, HtmlAttribute<M>, HtmlContent<M>> {

    public Html(Iterable<? extends HtmlAttribute<M>> attributes, Head head, Body body) {
        super(html, attributes, contents(head, body));
    }

    private static <M> List<HtmlContent<M>> contents(Head head, Body body) {
        return asList(head, body);
    }
}
