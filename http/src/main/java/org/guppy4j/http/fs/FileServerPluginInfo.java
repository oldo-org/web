package org.guppy4j.http.fs;

/**
 * @author Paul S. Hawke (paul.hawke@gmail.com)
 *         On: 9/14/13 at 8:09 AM
 */
public interface FileServerPluginInfo {

    String[] getMimeTypes();

    String[] getIndexFilesForMimeType(String mime);

    FileServerPlugin getWebServerPlugin(String mimeType);
}
