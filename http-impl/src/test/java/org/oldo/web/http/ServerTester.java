package org.oldo.web.http;

import org.junit.Test;
import org.oldo.web.http.util.ServerRunner;

public class ServerTester {

    @Test
    public void test() {
        String html = "<html>";
        final IServer server = request -> new Response(html);
        ServerRunner.executeInstance(new ServerDaemon("localhost", 9999, server));
    }

}
