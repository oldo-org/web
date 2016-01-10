package org.guppy4j.html.tag;

import org.guppy4j.html.Tag;
import org.guppy4j.html.marker.FieldsetContent;
import org.guppy4j.html.marker.LegendAttribute;
import org.guppy4j.html.marker.LegendContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.guppy4j.html.Element.legend;

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
