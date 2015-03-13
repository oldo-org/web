package org.guppy4j.web.http.fs;

import org.guppy4j.web.http.IRequest;
import org.guppy4j.web.http.Response;

import java.io.File;
import java.util.Map;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 9/14/13 at 8:09 AM
 */
public interface FileServerPlugin {

    void initialize(Map<String, String> commandLineOptions);

    boolean canServeUri(String uri, File rootDir);

    Response serveFile(String uri, Map<String, String> headers,
                       IRequest request, File file, String mimeType);
}
