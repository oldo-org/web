package org.guppy4j.samples;

import org.guppy4j.html.attribute.type.LanguageCode;
import org.guppy4j.html.model.Variable;

/**
 * Sample model for testing
 */
public interface Model {

    LanguageCode lang();

    String title();

    Iterable<String> names();

    Variable<String> name();

    Part part();
}
