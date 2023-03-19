package org.oldo.samples;

import org.oldo.html.attribute.type.LanguageCode;
import org.oldo.html.model.Variable;

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
