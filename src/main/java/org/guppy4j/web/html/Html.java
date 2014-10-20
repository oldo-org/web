package org.guppy4j.web.html;

import java.util.Arrays;

import org.guppy4j.web.Renderer;

/**
 * TODO: Document this
 */
public class Html extends Tag {

    public static class HtmlBuilder {
        private final HtmlAttribute[] attributes;

        private HtmlBuilder(HtmlAttribute... attributes) {
            this.attributes = attributes;
        }

        public Html _(Head head, Body body) {
            return new Html(Arrays.asList(attributes), head, body);
        }
    }

    private final Iterable<HtmlAttribute> attributes;
    private final Head head;
    private final Body body;

    public Html(Iterable<HtmlAttribute> attributes, Head head, Body body) {
        this.attributes = attributes;
        this.head = head;
        this.body = body;
    }

    public static HtmlBuilder html(HtmlAttribute... attributes) {
        return new HtmlBuilder(attributes);
    }

    @Override
    public void render(Renderer renderer) {
        head.render(renderer);
        body.render(renderer);
    }
}
