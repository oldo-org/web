package org.oldo.web.http.util;

import org.oldo.web.http.ServerDaemon;

import java.io.IOException;

public class ServerRunner {

    public static void executeInstance(ServerDaemon serverDaemon) {
        try {
            serverDaemon.start();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
            System.exit(-1);
        }

        System.out.println("Server started, Hit Enter to stop.\n");

        try {
            int read = System.in.read();
        } catch (Throwable ignored) {
        }

        serverDaemon.stop();
        System.out.println("Server stopped.\n");
    }
}
