package org.oldo.web;

import org.oldo.web.html.Out;

/**
 * Renders by appending to an Appendable
 */
public final class Appender implements Out {

    private final Appendable a;

    public Appender(Appendable appendable) {
        a = appendable;
    }

    @Override
    public void write(final String s) {
        Functions.tryTo(() -> a.append(s));
    }

    @Override
    public void write(final char c) {
        Functions.tryTo(() -> a.append(c));
    }
}
