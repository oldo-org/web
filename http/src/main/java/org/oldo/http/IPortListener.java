package org.oldo.http;

/**
 * Listens to a port
 */
public interface IPortListener {

    int getListeningPort();

    boolean isAlive();
}
