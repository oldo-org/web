package org.oldo.html.tag;

import org.oldo.html.Tag;
import org.oldo.html.marker.BodyContent;
import org.oldo.html.marker.DivContent;
import org.oldo.html.marker.FormAttribute;
import org.oldo.html.marker.FormContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.html.Element.form;

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
