package org.oldo.web.html.tag;

import org.oldo.web.html.Tag;
import org.oldo.web.html.marker.FieldsetContent;
import org.oldo.web.html.marker.LegendAttribute;
import org.oldo.web.html.marker.LegendContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.web.html.Element.legend;

/**
 * The 'legend' tag
 */
public class Legend<M> extends Tag<M, LegendAttribute<M>, LegendContent<M>>
    implements FieldsetContent<M> {

    @SafeVarargs
    public static <M> Legend<M> legend(LegendContent<M>... contents) {
        return new Legend<>(emptyList(), asList(contents));
    }

    public Legend(Iterable<LegendAttribute<M>> attributes,
                Iterable<LegendContent<M>> contents) {
        super(legend, attributes, contents);
    }
}
