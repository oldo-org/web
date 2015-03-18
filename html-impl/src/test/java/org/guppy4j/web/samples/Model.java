package org.guppy4j.web.samples;

import org.guppy4j.web.html.attribute.type.LanguageCode;
import org.guppy4j.web.html.model.Variable;

/**
 * Sample model for testing
 */
public interface Model {

    LanguageCode lang();

    String title();

    Iterable<String> names();

    Variable<String> name();
}