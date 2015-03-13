package org.guppy4j.web.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Handles one session, i.e. parses the HTTP request and returns the response.
 */
public interface IRequest {

    void handleBy(IServer server) throws IOException;

    Map<String, String> getParms();

    Map<String, String> getHeaders();

    /**
     * @return the path part of the URL.
     */
    String getUri();

    String getQueryParameterString();

    Method getMethod();

    InputStream getInputStream();

    Cookies getCookies();

    /**
     * Adds the files in the request body to the files map.
     *
     * @param files Map to modify
     */
    void parseBody(Map<String, String> files) throws IOException, ResponseException;
}
