package org.oldo.web.test;

import org.oldo.web.html.attribute.type.LanguageCode;
import org.oldo.web.html.model.Variable;

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
