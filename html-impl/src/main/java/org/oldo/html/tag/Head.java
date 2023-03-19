package org.oldo.html.tag;

import org.oldo.html.Tag;
import org.oldo.html.marker.HeadAttribute;
import org.oldo.html.marker.HeadContent;
import org.oldo.html.marker.HtmlContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.Checks.requireEqual;
import static org.oldo.Iterables.stream;
import static org.oldo.html.Element.head;

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
