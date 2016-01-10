package org.guppy4j.html.tag;

import org.guppy4j.html.Tag;
import org.guppy4j.html.marker.HtmlAttribute;
import org.guppy4j.html.marker.HtmlContent;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.guppy4j.html.Element.html;

/**
 * The "html" tag
 */
public class Html<M> extends Tag<M, HtmlAttribute<M>, HtmlContent<M>> {

    public static <M> Html<M> html(Iterable<? extends HtmlAttribute<M>> attributes,
                                   Head<M> head, Body<M> body) {
        return new Html<>(attributes, head, body);
    }

    public static <M> Html<M> html(Head<M> head, Body<M> body) {
        return new Html<>(emptyList(), head, body);
    }

    public Html(Iterable<? extends HtmlAttribute<M>> attributes, Head head, Body body) {
        super(html, attributes, contents(head, body));
    }

    private static <M> List<HtmlContent<M>> contents(Head head, Body body) {
        return asList(head, body);
    }
}
