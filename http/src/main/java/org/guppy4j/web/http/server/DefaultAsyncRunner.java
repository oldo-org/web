package org.guppy4j.web.http.server;

/**
 * Default threading strategy for HttpServer.
 * <p/>
 * <p>By default, the server spawns a new Thread for every incoming request.  These are set
 * to <i>daemon</i> status, and named according to the request number.  The name is
 * useful when profiling the application.</p>
 */
public class DefaultAsyncRunner implements AsyncRunner {
    private long requestCount;

    @Override
    public void exec(Runnable code) {
        ++requestCount;
        Thread t = new Thread(code);
        t.setDaemon(true);
        t.setName("HttpServer Request Processor (#" + requestCount + ")");
        t.start();
    }
}
