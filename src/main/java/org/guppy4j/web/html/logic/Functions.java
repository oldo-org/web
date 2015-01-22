package org.guppy4j.web.html.logic;

import java.io.IOException;
import java.util.function.Function;

/**
 * Helper functions
 */
public class Functions {

    public static <M, T> Function<M, T> is(T value) {
        return m -> value;
    }

    public static void tryTo(DoSomething<IOException> ds) {
        try {
            ds.doIt();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
