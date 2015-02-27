package org.guppy4j.web.http;

/**
 * TODO: Document this briefly
 */
public final class ResponseException extends Exception {

    private final Status status;

    public ResponseException(Status status, String message) {
        super(message);
        this.status = status;
    }

    public ResponseException(Status status, String message, Exception e) {
        super(message, e);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
