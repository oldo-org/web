package org.guppy4j.html;

import org.guppy4j.html.attribute.Attribute;

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
    public final void render(Out r, M m) {
        open(r, m);
        if (contents != null) {
            for (C content : contents) {
                content.render(r, m);
            }
        }
        close(r);
    }

    private void open(Out r, M m) {
        r.write('<');
        r.write(element.name());
        if (attributes != null) {
            for (A attribute : attributes) {
                attribute.render(r, m);
            }
        }
        r.write('>');
    }

    private void close(Out r) {
        r.write("</" + element + ">");
    }
}
