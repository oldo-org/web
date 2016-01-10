package org.guppy4j.html.tag;

import org.guppy4j.html.Tag;
import org.guppy4j.html.marker.FieldsetAttribute;
import org.guppy4j.html.marker.FieldsetContent;
import org.guppy4j.html.marker.FormContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.guppy4j.html.Element.fieldset;

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
