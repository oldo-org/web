package org.guppy4j.http;

/**
 * Pluggable strategy for asynchronously executing requests.
 */
public interface RequestExecutor {

    void execute(Runnable code);

}
