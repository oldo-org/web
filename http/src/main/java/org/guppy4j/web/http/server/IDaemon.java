package org.guppy4j.web.http.server;

import java.io.IOException;

/**
 * TODO: Document this briefly
 */
public interface IDaemon {

    void start() throws IOException;

    void stop();

}
