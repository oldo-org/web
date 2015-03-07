package org.guppy4j.web.html.content;

import org.guppy4j.web.html.logic.Mapping;
import org.guppy4j.web.html.marker.BodyContent;
import org.guppy4j.web.html.marker.SpanContent;
import org.guppy4j.web.html.marker.TitleContent;
import org.guppy4j.web.html.model.Variable;
import org.guppy4j.web.html.render.Renderer;

import java.util.function.Function;

import static org.guppy4j.web.html.logic.Functions.is;

/**
 * Text nodes
 */
public class Text<M> implements TitleContent<M>, BodyContent<M>, SpanContent<M> {

    public static <M> Text<M> $(String value) {
        return new Text<>(is(value));
    }

    public static <M> Text<M> $(Function<M, String> value) {
        return new Text<>(value);
    }

    public static <M> Text<M> $(Mapping<M, Variable<?>> value) {
        return new Text<>(value);
    }

    private final Function<M, ?> value;

    public Text(Function<M, ?> value) {
        this.value = value;
    }

    public Text(Mapping<M, Variable<?>> value) {
        this.value = (m -> value.map(m).get());
    }

    @Override
    public void render(Renderer renderer, M model) {
        final Object o = value.apply(model);
        renderer.render(o == null ? "" : o.toString());
    }
}
