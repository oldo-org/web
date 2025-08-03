package org.oldo.web.http;

/**
 * Listens to a port
 */
public interface IPortListener {

    int getListeningPort();

    boolean isAlive();
}
