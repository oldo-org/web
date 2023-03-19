package org.oldo.html.marker;

import org.oldo.html.Content;

public interface HeadContent<M> extends Content<M> {

    default boolean isTitle() {
        return false;
    }
}
