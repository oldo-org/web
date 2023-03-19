package org.oldo.html.attribute;

import org.oldo.html.attribute.type.LanguageCode;
import org.oldo.html.marker.HtmlAttribute;

import java.util.function.Function;

/**
 * HTML "lang" attribute
 */
public class Lang<M> extends AttributeBase<M> implements HtmlAttribute<M> {

    public Lang(Function<M, LanguageCode> value) {
        super("lang", value);
    }
}
