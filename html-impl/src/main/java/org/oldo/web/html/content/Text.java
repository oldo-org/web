package org.oldo.web.html.content;

import org.oldo.web.html.Out;
import org.oldo.web.html.logic.Mapping;
import org.oldo.web.html.marker.*;
import org.oldo.web.html.model.Variable;

import java.util.function.Function;

import static org.oldo.web.Functions.is;

/**
 * Text nodes
 */
public final class Text<M> implements TitleContent<M>, BodyContent<M>, SpanContent<M>,
        LegendContent<M>, LabelContent<M> {

    public static <M> Text<M> $(String value) {
        return new Text<>(is(value));
    }

    public static <M> Text<M> $(Function<M, ?> value) {
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
