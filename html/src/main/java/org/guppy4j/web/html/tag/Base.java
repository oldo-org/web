package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.content.type.Metadata;
import org.guppy4j.web.html.marker.BaseAttribute;
import org.guppy4j.web.html.marker.BaseContent;

import static org.guppy4j.web.html.tag.Element.base;

/**
 * TODO: Document this!
 */
public class Base<M> extends Tag<M, BaseAttribute<M>, BaseContent<M>>
    implements Metadata<M> {

    public Base(Iterable<BaseAttribute<M>> attributes,
                Iterable<BaseContent<M>> contents) {
        super(base, attributes, contents);
    }
}
