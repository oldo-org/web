package org.guppy4j.web.html.attribute;

import org.guppy4j.web.html.attribute.type.LanguageCode;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Mix-in methods for attribute creation using method names
 * that match the HTML5 attribute names
 */
public interface AttributeFactory {

    default Lang lang(LanguageCode value) {
        return new Lang(value);
    }

    default <T> List<T> $(T... attributes) {
        return asList(attributes);
    }
}
