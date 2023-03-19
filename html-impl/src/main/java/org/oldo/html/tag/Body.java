package org.oldo.html.tag;

import org.oldo.html.Tag;
import org.oldo.html.marker.BodyAttribute;
import org.oldo.html.marker.BodyContent;
import org.oldo.html.marker.HtmlContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.html.Element.body;

/**
 * The 'body' tag
 */
public class Body<M> extends Tag<M, BodyAttribute<M>, BodyContent<M>>
    implements HtmlContent<M> {

    @SafeVarargs
    public static <M> Body<M> body(BodyContent<M>... contents) {
        return new Body<>(emptyList(), asList(contents));
    }

    public Body(Iterable<BodyAttribute<M>> attributes,
                Iterable<BodyContent<M>> contents) {
        super(body, attributes, contents);
    }
}
