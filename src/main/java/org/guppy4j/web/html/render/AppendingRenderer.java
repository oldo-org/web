package org.guppy4j.web.html.render;

import java.io.IOException;

/**
 * Renders by appending to an Appendable
 */
public class AppendingRenderer implements Renderer {

    private final Appendable a;

    public AppendingRenderer(Appendable appendable) {
        this.a = appendable;
    }

    @Override
    public void render(String s) {
        try {
            a.append(s);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void render(char c) {
        try {
            a.append(c);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
