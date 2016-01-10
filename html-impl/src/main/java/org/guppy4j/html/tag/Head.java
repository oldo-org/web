package org.guppy4j.html.tag;

import org.guppy4j.html.Tag;
import org.guppy4j.html.marker.HeadAttribute;
import org.guppy4j.html.marker.HeadContent;
import org.guppy4j.html.marker.HtmlContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.guppy4j.Checks.requireEqual;
import static org.guppy4j.Iterables.stream;
import static org.guppy4j.html.Element.head;

/**
 * The 'head' tag
 */
public class Head<M> extends Tag<M, HeadAttribute<M>, HeadContent<M>>
        implements HtmlContent<M> {

    @SafeVarargs
    public static <M> Head<M> head(HeadContent<M>... contents) {
        return new Head<>(emptyList(), asList(contents));
    }

    public Head(Iterable<HeadAttribute<M>> attributes,
                Iterable<HeadContent<M>> contents) {
        super(head, attributes, contents);
        checkTitle(contents);
    }

    private void checkTitle(Iterable<HeadContent<M>> contents) {
        final long titleCount = stream(contents).filter(HeadContent::isTitle).count();
        requireEqual(1L, titleCount, "Invalid number of title elements");
    }
}
