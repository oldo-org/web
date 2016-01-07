package org.guppy4j.http;

import io.undertow.server.HttpServerExchange;

import java.io.IOException;

/**
 * HTTP request based on an undertow.io exchange object
 */
public final class UndertowRequest implements Request {

    private static final char LINE_SEPARATOR = '\n';

    private final HttpServerExchange hse;
    private final ResourcePath resourcePath;

    public UndertowRequest(HttpServerExchange hse) {
        this.hse = hse;
        resourcePath = new ResourcePathImpl(hse.getRequestPath());
    }

    @Override
    public Response startBlockingResponse() {
        hse.startBlocking();
        return new UndertowResponse(hse);
    }

    @Override
    public void appendTo(Appendable a) {
        addLine(a, "DEBUG INFO");
        addLine(a, val("requestURI", hse.getRequestURI()));
        addLine(a, val("relativePath", path().toString()));
        addLine(a, val("requestPath", hse.getRequestPath()));
        addLine(a, val("queryString", hse.getQueryString()));
    }

    @Override
    public ResourcePath path() {
        return resourcePath;
    }

    private static void addLine(Appendable a, CharSequence s) {
        try {
            a.append(s).append(LINE_SEPARATOR);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static CharSequence val(String name, String value) {
        return name + " = " + value;
    }
}
