package org.guppy4j.web.html.tag;

import org.guppy4j.web.html.attribute.type.LanguageCode;
import org.guppy4j.web.html.model.Variable;
import org.guppy4j.web.html.model.VariableImpl;
import org.guppy4j.web.html.render.AppendingRenderer;
import org.guppy4j.web.html.render.Renderer;
import org.guppy4j.web.samples.Model;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.guppy4j.web.html.attribute.AttributeFactory.lang;
import static org.guppy4j.web.html.attribute.AttributeFactory.with;
import static org.guppy4j.web.html.content.Text.$;
import static org.guppy4j.web.html.logic.LogicFactory.forEach;
import static org.guppy4j.web.html.tag.Body.body;
import static org.guppy4j.web.html.tag.Head.head;
import static org.guppy4j.web.html.tag.Html.html;
import static org.guppy4j.web.html.tag.Span.span;
import static org.guppy4j.web.html.tag.Title.title;

/**
 * Tests factory methods in combination
 */
public class RenderTest {

    @Test
    public void test() {

        final Html<Model> html =

            html(with(lang(Model::lang)),
                head(
                    title(
                        $(Model::title)
                    )
                ),
                body(
                    forEach(Model::names, Model::name,
                        span(
                            $(Model::name),
                            $(" !")
                        )
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
                return asList("a", "b", "c");
            }

            @Override
            public Variable<String> name() {
                return name;
            }
        };
    }
}
