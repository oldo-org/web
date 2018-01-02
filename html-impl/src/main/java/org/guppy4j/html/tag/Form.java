package org.guppy4j.html.tag;

import org.guppy4j.html.Tag;
import org.guppy4j.html.marker.BodyContent;
import org.guppy4j.html.marker.DivContent;
import org.guppy4j.html.marker.FormAttribute;
import org.guppy4j.html.marker.FormContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.guppy4j.html.Element.form;

/**
 * The 'form' tag
 */
public class Form<M> extends Tag<M, FormAttribute<M>, FormContent<M>>
        implements BodyContent<M>, DivContent<M> {

    @SafeVarargs
    public static <M> Form<M> form(FormContent<M>... contents) {
        return new Form<>(emptyList(), asList(contents));
    }

    public Form(Iterable<FormAttribute<M>> attributes,
                Iterable<FormContent<M>> contents) {
        super(form, attributes, contents);
    }
}
