package org.guppy4j.html.render;

/**
 * Something that can render itself using a renderer
 */
@FunctionalInterface
public interface Renderable<M> {

    void render(Renderer renderer, M model);

}
