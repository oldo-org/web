package org.guppy4j.http;

import io.undertow.Undertow;

/**
 * Wraps an Undertow HTTP server
 */
public final class UndertowServer implements Server {

    private final Undertow undertow;

    public UndertowServer(Undertow undertow) {
        this.undertow = undertow;
    }

    @Override
    public void start() {
        undertow.start();
    }

    @Override
    public void stop() {
        undertow.stop();
    }
}
