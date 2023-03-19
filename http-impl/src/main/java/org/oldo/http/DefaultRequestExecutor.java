package org.oldo.http;

import org.oldo.http.RequestExecutor;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Default threading strategy for HttpServer.
 * <p/>
 * <p>By default, the server spawns a new Thread for every incoming request.  These are set
 * to <i>daemon</i> status, and named according to the request number.  The name is
 * useful when profiling the application.</p>
 */
public class DefaultRequestExecutor implements RequestExecutor {

    private final AtomicLong requestCount = new AtomicLong();

    @Override
    public void execute(Runnable code) {
        final Thread t = new Thread(code);
        t.setDaemon(true);
        t.setName("HttpServer Request Processor (#" + requestCount.getAndIncrement() + ")");
        t.start();
    }
}
