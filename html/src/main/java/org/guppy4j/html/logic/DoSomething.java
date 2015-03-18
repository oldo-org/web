package org.guppy4j.html.logic;

/**
 * Execution that can throw an exception
 */
public interface DoSomething<E extends Exception> {

    void doIt() throws E;
}
