package org.oldo.html.tag;

import org.oldo.html.Tag;
import org.oldo.html.marker.FieldsetAttribute;
import org.oldo.html.marker.FieldsetContent;
import org.oldo.html.marker.FormContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.html.Element.fieldset;

/**
 * The 'fieldset' tag
 */
public class Fieldset<M> extends Tag<M, FieldsetAttribute<M>, FieldsetContent<M>>
        implements FormContent<M> {

    @SafeVarargs
    public static <M> Fieldset<M> fieldset(FieldsetContent<M>... contents) {
        return new Fieldset<>(emptyList(), asList(contents));
    }

    public Fieldset(Iterable<FieldsetAttribute<M>> attributes,
                    Iterable<FieldsetContent<M>> contents) {
        super(fieldset, attributes, contents);
    }
}
