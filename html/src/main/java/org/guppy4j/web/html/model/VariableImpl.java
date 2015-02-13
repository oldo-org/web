package org.guppy4j.web.html.model;

/**
 * TODO: Document this!
 */
public class VariableImpl<T> implements Variable<T> {

    private T t;

    @Override
    public void set(T t) {
        this.t = t;
    }

    @Override
    public T get() {
        return t;
    }
}
