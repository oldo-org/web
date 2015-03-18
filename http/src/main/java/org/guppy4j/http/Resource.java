package org.guppy4j.http;

import java.util.function.Predicate;

/**
 * A resource in the HTTP sense, also known as "web resource",
 * within a "RESTful architecture"
 *
 * @see <a href="http://rest.elkstein.org/">REST</a>
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
