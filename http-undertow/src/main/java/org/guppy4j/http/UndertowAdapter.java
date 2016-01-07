package org.guppy4j.http;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

/**
 * HTTP exchange requestHandler adapted to undertow.io
 */
public final class UndertowAdapter implements HttpHandler {

    private final RequestHandler requestHandler;
    private final HttpHandler fileHandler;
    private final String fileResourceName;

    public UndertowAdapter(RequestHandler requestHandler,
                           HttpHandler fileHandler,
                           String fileResourceName) {
        this.requestHandler = requestHandler;
        this.fileHandler = fileHandler;
        this.fileResourceName = fileResourceName;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        final Request request = new UndertowRequest(exchange);
        if (fileResourceName.equals(request.path().resourceName())) {
            handleFileRequest(exchange);
        } else {
            requestHandler.handle(request);
        }
    }

    private void handleFileRequest(HttpServerExchange exchange) {
        try {
            fileHandler.handleRequest(exchange);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
