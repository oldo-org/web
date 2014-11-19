package org.guppy4j.web.samples;

import org.guppy4j.web.html.tag.Html;

import static org.guppy4j.web.html.attribute.AttributeFactory.$;
import static org.guppy4j.web.html.attribute.AttributeFactory.lang;
import static org.guppy4j.web.html.attribute.type.LanguageCode.en;
import static org.guppy4j.web.html.tag.TagFactory.body;
import static org.guppy4j.web.html.tag.TagFactory.head;
import static org.guppy4j.web.html.tag.TagFactory.html;

/**
 * An Html sample
 */
public class MyHtml {

    private final Html html =

            html($(lang(en)),
                    head(
                        /*
                        meta(charset(utf_8)),
                        meta(description("Oliver Doepner")),
                        meta(robots("index, nofollow")),
                        title("Oliver Doepner")
                        */
                    ),
                    body(
                        /*
                        div(id("navi"))._(
                                iframe(src("navi.html"))
                        )
                        */
                    )
            );

}
