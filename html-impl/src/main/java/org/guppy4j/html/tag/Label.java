package org.guppy4j.html.tag;

import org.guppy4j.html.Tag;
import org.guppy4j.html.marker.FieldsetContent;
import org.guppy4j.html.marker.FormContent;
import org.guppy4j.html.marker.LabelAttribute;
import org.guppy4j.html.marker.LabelContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.guppy4j.html.Element.label;

/**
 * The 'label' tag
 */
public class Label<M> extends Tag<M, LabelAttribute<M>, LabelContent<M>>
    implements FormContent<M>, FieldsetContent<M> {

    @SafeVarargs
    public static <M> Label<M> label(LabelContent<M>... contents) {
        return new Label<>(emptyList(), asList(contents));
    }

    public Label(Iterable<LabelAttribute<M>> attributes,
                Iterable<LabelContent<M>> contents) {
        super(label, attributes, contents);
    }
}
