package org.guppy4j;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Util methods for instances of Iterable
 */
public class Iterables {
    public static <T> Stream<T> stream(Iterable<T> contents) {
        return StreamSupport.stream(contents.spliterator(), false);
    }
}
