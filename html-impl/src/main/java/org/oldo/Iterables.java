package org.oldo;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Util methods for instances of Iterable
 */
public class Iterables {

    public static <T> Stream<T> stream(Iterable<T> contents) {
        return stream(contents, false);
    }

    public static <T> Stream<T> parallelStream(Iterable<T> contents) {
        return stream(contents, true);
    }

    private static <T> Stream<T> stream(Iterable<T> contents, boolean parallel) {
        return StreamSupport.stream(contents.spliterator(), parallel);
    }
}
