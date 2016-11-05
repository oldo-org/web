package org.guppy4j.html.content;

import org.guppy4j.html.Out;
import org.guppy4j.html.logic.Mapping;
import org.guppy4j.html.marker.BodyContent;
import org.guppy4j.html.marker.LabelContent;
import org.guppy4j.html.marker.LegendContent;
import org.guppy4j.html.marker.SpanContent;
import org.guppy4j.html.marker.TitleContent;
import org.guppy4j.html.model.Variable;

import java.util.function.Function;

import static org.guppy4j.Functions.is;

/**
 * Text nodes
 */
public final class Text<M> implements TitleContent<M>, BodyContent<M>, SpanContent<M>,
        LegendContent<M>, LabelContent<M> {

    public static <M> Text<M> $(String value) {
        return new Text<>(is(value));
    }

    public static <M> Text<M> $(Function<M, Object> value) {
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
    public void render(Out out, M model) {
        out.write(String.valueOf(value.apply(model)));
    }
}
