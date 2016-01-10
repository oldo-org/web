package org.guppy4j.html.content;

import org.guppy4j.html.logic.Mapping;
import org.guppy4j.html.marker.*;
import org.guppy4j.html.model.Variable;
import org.guppy4j.html.render.Renderer;

import java.util.function.Function;

import static org.guppy4j.html.logic.Functions.is;

/**
 * Text nodes
 */
public final class Text<M> implements TitleContent<M>, BodyContent<M>, SpanContent<M>,
        LegendContent<M>, LabelContent<M> {

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
        this.value = m -> value.map(m).get();
    }

    @Override
    public void render(Renderer renderer, M model) {
        final Object o = value.apply(model);
        renderer.render(o == null ? "" : o.toString());
    }
}
