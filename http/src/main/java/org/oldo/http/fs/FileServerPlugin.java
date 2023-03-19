package org.oldo.http.fs;

import org.oldo.http.IRequest;
import org.oldo.http.IResponse;

import java.io.File;
import java.util.Map;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 9/14/13 at 8:09 AM
 */
public interface FileServerPlugin {

    void initialize(Map<String, String> commandLineOptions);

    boolean canServeUri(String uri, File rootDir);

    IResponse serveFile(String uri, Map<String, String> headers,
                        IRequest request, File file, String mimeType);
}
