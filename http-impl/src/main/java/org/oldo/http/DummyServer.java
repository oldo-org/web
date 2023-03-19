package org.oldo.http;

import org.oldo.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A server that serves nothing
 * (just some sample code)
 */
public class DummyServer implements IServer {

    @Override
    public Response serve(IRequest request) {

        final Map<String, String> files = new HashMap<>();
        final Method method = request.getMethod();
        if (Method.PUT.equals(method) || Method.POST.equals(method)) {
            try {
                request.parseBody(files);
            } catch (IOException ioe) {
                return new Response(Status.INTERNAL_ERROR, IServer.MIME_PLAINTEXT, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            } catch (ResponseException re) {
                return new Response(re.getStatus(), IServer.MIME_PLAINTEXT, re.getMessage());
            }
        }

        final Map<String, String> parms = request.getParms();
        parms.put(IServer.QUERY_STRING_PARAMETER, request.getQueryParameterString());
        return new Response(Status.NOT_FOUND, IServer.MIME_PLAINTEXT, "Not Found");
    }
}
