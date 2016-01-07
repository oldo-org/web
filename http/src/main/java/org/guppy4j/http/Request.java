package org.guppy4j.http;

/**
 * HTTP Request
 */
public interface Request {

    ResourcePath path();

    Response startBlockingResponse();

    void appendTo(Appendable appendable);
}
