package org.guppy4j.html.marker;

import org.guppy4j.html.Content;

public interface HeadContent<M> extends Content<M> {

    default boolean isTitle() {
        return false;
    }
}
