package org.oldo.web.http.tempfiles;

/**
 * Default strategy for creating and cleaning up temporary io.
 */
public class DefaultTempFilesFactory implements TempFilesFactory {

    @Override
    public TempFiles create() {
        return new DefaultTempFiles();
    }
}
