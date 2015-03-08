package org.guppy4j.web.http;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import static org.guppy4j.web.http.util.ConnectionUtil.close;

/**
 * Manages socket connections
 */
public class Connections {

    private final Set<Socket> openConnections = new HashSet<>();

    /**
     * Registers that a new connection has been set up.
     *
     * @param socket the {@link java.net.Socket} for the connection.
     */
    public synchronized void add(Socket socket) {
        openConnections.add(socket);
    }

    /**
     * Registers that a connection has been closed
     *
     * @param socket the {@link java.net.Socket} for the connection.
     */
    public synchronized void remove(Socket socket) {
        openConnections.remove(socket);
    }

    /**
     * Forcibly closes all connections that are open.
     */
    public synchronized void closeAll() {
        for (Socket socket : openConnections) {
            close(socket);
        }
    }
}
