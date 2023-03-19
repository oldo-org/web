package org.oldo.http;

import java.io.IOException;

/**
 * A daemon process can be started and stopped
 */
public interface IDaemon {

    void start() throws IOException;

    void stop();

    boolean wasStarted();
}
