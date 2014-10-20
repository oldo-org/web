package org.guppy4j.web.html;

import java.util.Arrays;

import org.guppy4j.web.Renderable;
import org.guppy4j.web.Renderer;

/**
 * TODO: Document this
 */
public class Tag implements Renderable {

    private final Iterable<Tag> content;

    public Tag(Tag... content) {
        this.content = Arrays.asList(content);
    }

    public Tag(Attribute... attributes) {

    }

    @Override
    public void render(Renderer renderer) {
        for (Tag tag : content) {
            tag.render(renderer);
        }
    }
}
