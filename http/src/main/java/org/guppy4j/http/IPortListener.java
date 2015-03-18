package org.guppy4j.http;

/**
 * Listens to a port
 */
public interface IPortListener {

    int getListeningPort();

    boolean isAlive();
}
