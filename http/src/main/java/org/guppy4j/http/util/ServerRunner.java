package org.guppy4j.http.util;

import org.guppy4j.http.ServerDaemon;

import java.io.IOException;

public class ServerRunner {

    public static void run(Class<? extends ServerDaemon> serverClass) {
        try {
            executeInstance(serverClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeInstance(ServerDaemon serverDaemon) {
        try {
            serverDaemon.start();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
            System.exit(-1);
        }

        System.out.println("Server started, Hit Enter to stop.\n");

        try {
            System.in.read();
        } catch (Throwable ignored) {
        }

        serverDaemon.stop();
        System.out.println("Server stopped.\n");
    }
}
