package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.attribute.type.LanguageCode;
import org.guppy4j.web.html.model.Variable;
import org.guppy4j.web.html.model.VariableImpl;
import org.guppy4j.web.html.render.AppendingRenderer;
import org.guppy4j.web.html.render.Renderer;
import org.guppy4j.web.samples.Model;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;

import static org.guppy4j.web.html.attribute.AttributeFactory.lang;
import static org.guppy4j.web.html.attribute.AttributeFactory.with;
import static org.guppy4j.web.html.logic.LogicFactory.forEach;
import static org.guppy4j.web.html.tag.TagFactory.$;
import static org.guppy4j.web.html.tag.TagFactory.body;
import static org.guppy4j.web.html.tag.TagFactory.head;
import static org.guppy4j.web.html.tag.TagFactory.html;
import static org.guppy4j.web.html.tag.TagFactory.span;
import static org.guppy4j.web.html.tag.TagFactory.title;

/**
 * Tests factory methods in combination
 */
public class FactoryTest {

    @Test
    public void test() {

        final Function<Model, String> name = m -> m.name().get();

        final Html<Model> html =

            html(with(lang(Model::lang)),
                head(
                    title(
                        $(Model::title),
                        $(" : "),
                        forEach(Model::names, Model::name,
                            $(name)
                        )
                    )
                ),
                body(
                    span(
                        $(Model::title),
                        $(" !")
                    )
                )
            );

        final StringBuilder sb = new StringBuilder();
        final Renderer renderer = new AppendingRenderer(sb);
        final Model model = createTestModel();

        html.render(renderer, model);

        System.out.print(sb);
    }

    private Model createTestModel() {
        return new Model() {

            private final Variable<String> name = new VariableImpl<>();

            @Override
            public LanguageCode lang() {
                return LanguageCode.de;
            }

            @Override
            public String title() {
                return "Cool page";
            }

            @Override
            public Iterable<String> names() {
                return Arrays.asList("a", "b", "c");
            }

            @Override
            public Variable<String> name() {
                return name;
            }
        };
    }
}
