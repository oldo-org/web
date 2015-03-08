package org.guppy4j.web.http;

/**
 * Pluggable strategy for asynchronously executing requests.
 */
public interface RequestExecutor {

    void execute(Runnable code);

}
