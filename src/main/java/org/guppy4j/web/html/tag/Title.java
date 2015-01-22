package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.marker.HeadContent;
import org.guppy4j.web.html.marker.TitleAttribute;
import org.guppy4j.web.html.marker.TitleContent;

import static org.guppy4j.web.html.tag.Element.title;

/**
 * TODO: Document this!
 */
public class Title<M> extends Tag<M, TitleAttribute<M>, TitleContent<M>>
    implements HeadContent<M> {

    public Title(Iterable<TitleAttribute<M>> attributes,
                 Iterable<TitleContent<M>> contents) {
        super(title, attributes, contents);
    }
}
