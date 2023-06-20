package org.oldo.dbui;


import org.jsoup.Jsoup;
import org.junit.Test;
import org.oldo.Appender;
import org.oldo.html.Out;
import org.oldo.html.attribute.type.LanguageCode;
import org.oldo.html.model.Variable;
import org.oldo.html.model.VariableImpl;
import org.oldo.html.tag.Html;
import org.oldo.http.IServer;
import org.oldo.http.Response;
import org.oldo.http.ServerDaemon;

import static java.util.Arrays.asList;
import static org.oldo.html.attribute.AttributeFactory.lang;
import static org.oldo.html.attribute.AttributeFactory.with;
import static org.oldo.html.content.Text.$;
import static org.oldo.html.logic.LogicFactory.forEach;
import static org.oldo.html.logic.LogicFactory.forProperty;
import static org.oldo.html.tag.Body.body;
import static org.oldo.html.tag.Div.div;
import static org.oldo.html.tag.Form.form;
import static org.oldo.html.tag.Head.head;
import static org.oldo.html.tag.Html.html;
import static org.oldo.html.tag.Span.span;
import static org.oldo.html.tag.Title.title;
import static org.oldo.http.util.ServerRunner.executeInstance;


public class WebTester {

    @Test
    public void test() {
        final Html<Model> html = getHtml();
        final Model model = createTestModel();

        final StringBuilder htmlSource = new StringBuilder();
        final Out out = new Appender(htmlSource);
        html.render(out, model);

        final String prettyHtml = Jsoup.parse("<!DOCTYPE html>" + htmlSource).toString();
        final IServer server = request -> new Response(prettyHtml);

        executeInstance(new ServerDaemon("localhost", 9999, server));
    }

    private Html<Model> getHtml() {
        return
            html(with(lang(Model::lang)),
                head(
                        title($(Model::title))
                ),
                body(
                        form(

                        ),
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
