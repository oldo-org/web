package org.guppy4j;

import org.guppy4j.html.Out;

import static org.guppy4j.Functions.tryTo;

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
        tryTo(() -> a.append(s));
    }

    @Override
    public void write(final char c) {
        tryTo(() -> a.append(c));
    }
}
