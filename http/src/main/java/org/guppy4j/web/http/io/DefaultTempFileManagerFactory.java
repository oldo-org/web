package org.guppy4j.web.http.io;

/**
 * Default strategy for creating and cleaning up temporary io.
 */
public class DefaultTempFileManagerFactory implements TempFileManagerFactory {
    @Override
    public TempFileManager create() {
        return new DefaultTempFileManager();
    }
}
