package org.guppy4j.web.html.attribute;

import org.guppy4j.web.html.attribute.type.LanguageCode;
import org.guppy4j.web.html.marker.HtmlAttribute;

/**
 * HTML "lang" attribute
 */
public class Lang extends AttributeBase<LanguageCode>
        implements HtmlAttribute {

    public Lang(LanguageCode value) {
        super("lang", value);
    }
}
