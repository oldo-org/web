package org.oldo.web.html.marker;

import org.oldo.web.html.Content;

public interface HeadContent<M> extends Content<M> {

    default boolean isTitle() {
        return false;
    }
}
