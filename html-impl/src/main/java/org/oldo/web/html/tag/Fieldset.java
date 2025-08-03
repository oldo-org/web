package org.oldo.web.html.tag;

import org.oldo.web.html.Tag;
import org.oldo.web.html.marker.FieldsetAttribute;
import org.oldo.web.html.marker.FieldsetContent;
import org.oldo.web.html.marker.FormContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.web.html.Element.fieldset;

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
