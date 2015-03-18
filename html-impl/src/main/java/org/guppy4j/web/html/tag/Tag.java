package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.Content;
import org.guppy4j.web.html.attribute.Attribute;
import org.guppy4j.web.html.render.Renderable;
import org.guppy4j.web.html.render.Renderer;

import static java.util.Objects.requireNonNull;

/**
 * Tag base class
 */
public class Tag<M, A extends Attribute<M>, C extends Content<M>> implements Renderable<M> {

    private final Element element;
    private final Iterable<? extends A> attributes;
    private final Iterable<? extends C> contents;

    public Tag(Element element,
               Iterable<? extends A> attributes,
               Iterable<? extends C> contents) {
        requireNonNull(element, "element");
        requireNonNull(attributes, "attributes");
        requireNonNull(contents, "contents");
        this.element = element;
        this.attributes = attributes;
        this.contents = contents;
    }

    @Override
    public final void render(Renderer r, M m) {
        open(r, m);
        if (contents != null) {
            for (C content : contents) {
                content.render(r, m);
            }
        }
        close(r);
    }

    private void open(Renderer r, M m) {
        r.render('<');
        r.render(element.name());
        if (attributes != null) {
            for (A attribute : attributes) {
                attribute.render(r, m);
            }
        }
        r.render('>');
    }

    private void close(Renderer r) {
        r.render("</" + element + ">");
    }
}
