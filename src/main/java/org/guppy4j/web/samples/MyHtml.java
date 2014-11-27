package org.guppy4j.web.samples;

import org.guppy4j.web.html.attribute.AttributeFactory;
import org.guppy4j.web.html.tag.Html;
import org.guppy4j.web.html.tag.TagFactory;

import static org.guppy4j.web.html.attribute.type.LanguageCode.en;

/**
 * An Html sample
 */
public class MyHtml implements TagFactory, AttributeFactory {

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
