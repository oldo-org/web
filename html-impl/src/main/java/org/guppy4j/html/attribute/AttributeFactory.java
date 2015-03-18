package org.guppy4j.html.attribute;

import org.guppy4j.html.attribute.type.LanguageCode;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

/**
 * Attribute creation using method names
 * that match the HTML5 attribute names
 */
public final class AttributeFactory {

    private AttributeFactory() {
    }

    public static <M> Lang<M> lang(Function<M, LanguageCode> value) {
        return new Lang<>(value);
    }

    @SafeVarargs
    public static <T> List<T> with(T... attributes) {
        return asList(attributes);
    }
}
