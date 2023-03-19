package org.oldo.html.content;

import org.oldo.html.Out;
import org.oldo.html.logic.Mapping;
import org.oldo.html.marker.BodyContent;
import org.oldo.html.marker.LabelContent;
import org.oldo.html.marker.LegendContent;
import org.oldo.html.marker.SpanContent;
import org.oldo.html.marker.TitleContent;
import org.oldo.html.model.Variable;

import java.util.function.Function;

import static org.oldo.Functions.is;

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
