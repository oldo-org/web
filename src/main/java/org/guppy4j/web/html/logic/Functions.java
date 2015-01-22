package org.guppy4j.web.html.logic;

import java.util.function.Function;

/**
 * Helper functions
 */
public class Functions {

    public static <M, T> Function<M, T> is(T value) {
        return m -> value;
    }
}
