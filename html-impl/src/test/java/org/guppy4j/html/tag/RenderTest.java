package org.guppy4j.html.tag;

import org.guppy4j.Appender;
import org.guppy4j.html.Out;
import org.guppy4j.html.attribute.type.LanguageCode;
import org.guppy4j.html.model.Variable;
import org.guppy4j.html.model.VariableImpl;
import org.guppy4j.samples.Model;
import org.guppy4j.samples.Part;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.guppy4j.html.attribute.AttributeFactory.lang;
import static org.guppy4j.html.attribute.AttributeFactory.with;
import static org.guppy4j.html.content.Text.$;
import static org.guppy4j.html.logic.LogicFactory.forEach;
import static org.guppy4j.html.logic.LogicFactory.forProperty;
import static org.guppy4j.html.tag.Body.body;
import static org.guppy4j.html.tag.Div.div;
import static org.guppy4j.html.tag.Head.head;
import static org.guppy4j.html.tag.Html.html;
import static org.guppy4j.html.tag.Span.span;
import static org.guppy4j.html.tag.Title.title;

/**
 * Tests factory methods in combination
 */
public class RenderTest {

    @Test
    public void test() {

        final Html<Model> html =

                html(with(lang(Model::lang)),
                        head(
                                title($(Model::title))
                        ),
                        body(
                                forEach(Model::names, Model::name,
                                        span(
                                                $(Model::name),
                                                $(" !")
                                        ),
                                        div()
                                ),
                                forProperty(Model::part,
                                        span(
                                                $(Part::id)
                                        )
                                )

                        )
                );

        final StringBuilder sb = new StringBuilder();
        final Out out = new Appender(sb);
        final Model model = createTestModel();

        html.render(out, model);

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

            @Override
            public Part part() {
                return () -> 4711;
            }
        };
    }
}
