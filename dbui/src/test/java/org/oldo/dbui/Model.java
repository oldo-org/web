package org.oldo.dbui;


import org.oldo.html.attribute.type.LanguageCode;
import org.oldo.html.model.Variable;

public interface Model {

    LanguageCode lang();

    String title();

    Iterable<String> names();

    Variable<String> name();

    Part part();
}
