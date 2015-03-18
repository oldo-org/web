package org.guppy4j.http.tempfiles;

/**
 * Temp file manager.
 * <p/>
 * <p>Temp file managers are created 1-to-1 with incoming requests, to create and cleanup
 * temporary io created as a result of handling the request.</p>
 */
public interface TempFiles {

    TempFile createNew() throws Exception;

    void clear();
}
