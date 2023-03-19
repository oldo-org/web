package org.oldo.http;

import org.oldo.http.util.ConnectionUtil;

import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.newSetFromMap;

/**
 * Manages socket connections
 */
public final class Connections {

    private final Set<Socket> set = newSetFromMap(new ConcurrentHashMap<>());

    /**
     * Registers that a new connection has been set up.
     *
     * @param socket the {@link java.net.Socket} for the connection.
     */
    public void add(Socket socket) {
        set.add(socket);
    }

    /**
     * Registers that a connection has been closed
     *
     * @param socket the {@link java.net.Socket} for the connection.
     */
    public void remove(Socket socket) {
        set.remove(socket);
    }

    /**
     * Forcibly closes all connections that are open.
     */
    public void closeAll() {
        set.forEach(ConnectionUtil::close);
    }
}
