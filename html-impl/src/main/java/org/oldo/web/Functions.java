package org.oldo.web;

import org.oldo.web.html.logic.DoSomething;

import java.util.function.Function;

/**
 * Helper functions
 */
public final class Functions {

    private Functions() {
        // no instances
    }

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
