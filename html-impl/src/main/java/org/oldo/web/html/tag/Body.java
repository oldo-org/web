package org.oldo.web.html.tag;

import org.oldo.web.html.Tag;
import org.oldo.web.html.marker.BodyAttribute;
import org.oldo.web.html.marker.BodyContent;
import org.oldo.web.html.marker.HtmlContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.web.html.Element.body;

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
