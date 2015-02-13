package org.guppy4j.web.html.render;

import static org.guppy4j.web.html.logic.Functions.tryTo;

/**
 * Renders by appending to an Appendable
 */
public class AppendingRenderer implements Renderer {

    private final Appendable a;

    public AppendingRenderer(Appendable appendable) {
        this.a = appendable;
    }

    @Override
    public void render(final String s) {
        tryTo(() -> a.append(s));
    }

    @Override
    public void render(final char c) {
        tryTo(() -> a.append(c));
    }
}
