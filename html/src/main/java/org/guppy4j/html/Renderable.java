package org.guppy4j.html;

import org.guppy4j.html.Out;

/**
 * Something that can render itself into an output handler using a model
 */
@FunctionalInterface
public interface Renderable<M> {

    void render(Out out, M model);

}
