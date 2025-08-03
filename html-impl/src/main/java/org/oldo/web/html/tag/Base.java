package org.oldo.web.html.tag;

import org.oldo.web.html.Tag;
import org.oldo.web.html.content.type.Metadata;
import org.oldo.web.html.marker.BaseAttribute;
import org.oldo.web.html.marker.BaseContent;

import static org.oldo.web.html.Element.base;

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
