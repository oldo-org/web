package org.guppy4j.web.html.marker;

import org.guppy4j.web.html.Content;

public interface HeadContent<M> extends Content<M> {

    default boolean isTitle() {
        return false;
    }
}
