package org.guppy4j.web.html.attribute;

import org.guppy4j.web.html.attribute.type.LanguageCode;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Attribute creation using method names
 * that match the HTML5 attribute names
 */
public class AttributeFactory {

    public static Lang lang(LanguageCode value) {
        return new Lang(value);
    }

    public static <T> List<T> $(T... attributes) {
        return asList(attributes);
    }
}
