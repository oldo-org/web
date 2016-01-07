package org.guppy4j.http;

/**
 * HTTP request handler
 */
public interface RequestHandler {

    Response handle(Request request);

}
