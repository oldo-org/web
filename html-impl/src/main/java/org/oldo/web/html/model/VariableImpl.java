package org.oldo.web.html.model;

/**
 * A mutable value holder aka "variable"
 * to be used in data tree models
 */
public final class VariableImpl<T> implements Variable<T> {

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
