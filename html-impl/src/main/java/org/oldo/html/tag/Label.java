package org.oldo.html.tag;

import org.oldo.html.Tag;
import org.oldo.html.marker.FieldsetContent;
import org.oldo.html.marker.FormContent;
import org.oldo.html.marker.LabelAttribute;
import org.oldo.html.marker.LabelContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.html.Element.label;

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
