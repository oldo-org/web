package org.guppy4j.web.html.logic;

import java.util.function.Function;

/**
 * Helper functions
 */
public class Functions {

    public static <M, T> Function<M, T> is(T value) {
        return m -> value;
    }

    public static <E extends Exception> void tryTo(DoSomething<E> ds) {
        try {
            ds.doIt();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
