package org.oldo.html.tag;

import org.oldo.html.Tag;
import org.oldo.html.content.type.Metadata;
import org.oldo.html.marker.TitleAttribute;
import org.oldo.html.marker.TitleContent;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.oldo.html.Element.title;

/**
 * The 'title' tag
 */
public class Title<M> extends Tag<M, TitleAttribute<M>, TitleContent<M>>
    implements Metadata<M> {

    @SafeVarargs
    public static <M> Title<M> title(TitleContent<M>... contents) {
        return new Title<>(emptyList(), asList(contents));
    }

    public Title(Iterable<TitleAttribute<M>> attributes,
                 Iterable<TitleContent<M>> contents) {
        super(title, attributes, contents);
    }

    @Override
    public boolean isTitle() {
        return true;
    }
}
