package org.oldo.http;

import org.oldo.http.IServer;
import org.oldo.http.util.ServerRunner;
import org.junit.Test;

public class ServerTester {

    @Test
    public void test() {
        String html = "<html>";
        final IServer server = request -> new Response(html);
        ServerRunner.executeInstance(new ServerDaemon("localhost", 9999, server));
    }

}
