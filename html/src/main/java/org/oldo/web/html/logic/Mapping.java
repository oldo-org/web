package org.oldo.web.html.logic;

/**
 * TODO: Document this briefly
 */
@FunctionalInterface
public interface Mapping<A, B> {

    B map(A a);
}
