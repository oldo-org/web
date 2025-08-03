package org.oldo.web.html.tag;

import org.oldo.web.html.Tag;
import org.oldo.web.html.marker.HtmlAttribute;
import org.oldo.web.html.marker.HtmlContent;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.web.html.Element.html;

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

    public Html(Iterable<? extends HtmlAttribute<M>> attributes, Head<M> head, Body<M> body) {
        super(html, attributes, contents(head, body));
    }

    private static <M> List<HtmlContent<M>> contents(Head<M> head, Body<M> body) {
        return asList(head, body);
    }
}
