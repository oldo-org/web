package org.guppy4j.web.http;

/**
 * Listens to a port
 */
public interface IPortListener {

    int getListeningPort();

    boolean isAlive();
}
