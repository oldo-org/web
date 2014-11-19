package org.guppy4j.web.html.render;

/**
 * Something that can render itself using a renderer
 */
public interface Renderable<T> {

    void render(Renderer renderer);

}
