package org.guppy4j.http;

import java.util.function.Predicate;

/**
 * TODO: Document this!
 */
public interface Resource<I, T> {

    /* CRUD */

    void put(T t); // updates t

    void post(T t); // creates t

    T get(I id); // retrieves T

    I delete(I id);

    Iterable<T> get(Predicate<T> s);

    Iterable<I> delete(Predicate<T> s);

    /* other http methods

    head()
    options()
    trace()

    */
}

