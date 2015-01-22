package org.guppy4j.web.html.attribute;

import org.guppy4j.web.html.attribute.type.LanguageCode;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

/**
 * Attribute creation using method names
 * that match the HTML5 attribute names
 */
public class AttributeFactory {

    public static <M> Lang<M> lang(Function<M, LanguageCode> value) {
        return new Lang<>(value);
    }

    @SafeVarargs
    public static <T> List<T> $(T... attributes) {
        return asList(attributes);
    }
}
