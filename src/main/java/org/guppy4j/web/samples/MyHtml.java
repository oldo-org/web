package org.guppy4j.web.samples;

import static org.guppy4j.web.html.Head.head;
import static org.guppy4j.web.html.Html.html;
import static org.guppy4j.web.html.Lang.en;
import static org.guppy4j.web.html.Lang.lang;

/**
 * TODO: Document this
 */
public class MyHtml {
    {
        html(lang(en))._(
            head()._(
                meta(charset(utf_8)),
                meta(description("Oliver Doepner")),
                meta(robots("index, nofollow")),
                title("Oliver Doepner")
            ),
            body()._(
               div(id("navi"))._(
                   iframe(src("navi.html"))
               )
            )
        );
    }
}
