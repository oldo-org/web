package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.Attribute;
import org.guppy4j.web.html.Content;
import org.guppy4j.web.html.Element;
import org.guppy4j.web.html.render.Renderable;
import org.guppy4j.web.html.render.Renderer;

/**
 * Tag base class
 */
public class Tag<A extends Attribute, C extends Content> implements Renderable {

    private final Element element;
    private final Iterable<? extends A> attributes;
    private final Iterable<? extends C> contents;

    public Tag(Element element,
               Iterable<? extends A> attributes,
               Iterable<? extends C> contents) {
        this.element = element;
        this.attributes = attributes;
        this.contents = contents;
    }

    @Override
    public final void render(Renderer renderer) {
        renderer.render(opening());
        if (contents != null) {
            for (C content : contents) {
                content.render(renderer);
            }
        }
        renderer.render(closing());
    }

    private String opening() {
        final StringBuilder sb = new StringBuilder();
        sb.append('<');
        sb.append(element);
        if (attributes != null) {
            for (A attribute : attributes) {
                sb.append(' ');
                sb.append(attribute.name());
                sb.append('=');
                sb.append('"');
                sb.append(attribute.value().toString());
                sb.append('"');
            }
        }
        sb.append(' ');
        sb.append('>');
        return sb.toString();
    }

    private String closing() {
        return "</" + element + ">";
    }
}
