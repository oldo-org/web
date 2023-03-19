package org.oldo.html.tag;

import org.oldo.html.Tag;
import org.oldo.html.content.type.Metadata;
import org.oldo.html.marker.BaseAttribute;
import org.oldo.html.marker.BaseContent;

import static org.oldo.html.Element.base;

/**
 * The 'base' tag
 */
public class Base<M> extends Tag<M, BaseAttribute<M>, BaseContent<M>>
    implements Metadata<M> {

    public Base(Iterable<BaseAttribute<M>> attributes,
                Iterable<BaseContent<M>> contents) {
        super(base, attributes, contents);
    }
}
