package org.oldo.html.tag;

import org.oldo.Appender;
import org.oldo.html.Out;
import org.oldo.html.attribute.type.LanguageCode;
import org.oldo.html.model.Variable;
import org.oldo.html.model.VariableImpl;
import org.oldo.samples.Model;
import org.oldo.samples.Part;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.oldo.html.attribute.AttributeFactory.lang;
import static org.oldo.html.attribute.AttributeFactory.with;
import static org.oldo.html.content.Text.$;
import static org.oldo.html.logic.LogicFactory.forEach;
import static org.oldo.html.logic.LogicFactory.forProperty;
import static org.oldo.html.tag.Body.body;
import static org.oldo.html.tag.Div.div;
import static org.oldo.html.tag.Head.head;
import static org.oldo.html.tag.Html.html;
import static org.oldo.html.tag.Span.span;
import static org.oldo.html.tag.Title.title;

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
