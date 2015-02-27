package org.guppy4j.web.http.files;

import org.guppy4j.web.http.ISession;
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
                       ISession session, File file, String mimeType);
}
