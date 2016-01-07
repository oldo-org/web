package org.guppy4j.http;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * HTTP response based on an undertow.io exchange object
 */
public final class UndertowResponse implements Response {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private final HttpServerExchange hse;

    public UndertowResponse(HttpServerExchange hse) {
        this.hse = hse;
    }

    @Override
    public Writer writer(String contentType) {
        hse.getResponseHeaders().put(Headers.CONTENT_TYPE, contentType);
        return new BufferedWriter(new OutputStreamWriter(hse.getOutputStream(), UTF8));
    }

    @Override
    public void done() {
        hse.endExchange();
    }
}
