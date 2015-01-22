package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.content.Text;
import org.guppy4j.web.html.marker.BodyContent;
import org.guppy4j.web.html.marker.HeadContent;
import org.guppy4j.web.html.marker.HtmlAttribute;
import org.guppy4j.web.html.marker.SpanContent;
import org.guppy4j.web.html.marker.TitleContent;

import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.guppy4j.web.html.logic.Functions.is;

/**
 * Tag creation using method names
 * that match the HTML5 tag names
 */
public class TagFactory {

    public static <M> Html<M> html(Iterable<? extends HtmlAttribute<M>> attributes,
                                   Head<M> head, Body<M> body) {
        return new Html<>(attributes, head, body);
    }

    @SafeVarargs
    public static <M> Head<M> head(HeadContent<M>... contents) {
        return new Head<>(null, asList(contents));
    }

    @SafeVarargs
    public static <M> Span<M> span(SpanContent<M>... contents) {
        return new Span<>(null, asList(contents));
    }

    @SafeVarargs
    public static <M> Body<M> body(BodyContent<M>... contents) {
        return new Body<>(null, asList(contents));
    }

    @SafeVarargs
    public static <M> Title<M> title(TitleContent<M>... contents) {
        return new Title<>(null, asList(contents));
    }

    public static <M> Text<M> text(String value) {
        return new Text<>(is(value));
    }

    public static <M> Text<M> text(Function<M, String> value) {
        return new Text<>(value);
    }
}
