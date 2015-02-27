package org.guppy4j.web.http.server;

/**
 * Pluggable strategy for asynchronously executing requests.
 */
public interface AsyncRunner {
    void exec(Runnable code);
}
