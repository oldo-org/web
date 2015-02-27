package org.guppy4j.web.http.files;

/**
 * Default strategy for creating and cleaning up temporary files.
 */
public class DefaultTempFileManagerFactory implements TempFileManagerFactory {
    @Override
    public TempFileManager create() {
        return new DefaultTempFileManager();
    }
}
