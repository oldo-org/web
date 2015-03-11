package org.guppy4j.web.http;

import org.guppy4j.web.TempFileManager;
import org.guppy4j.web.TempFileManagerFactory;

/**
 * Default strategy for creating and cleaning up temporary files.
 */
class DefaultTempFileManagerFactory implements TempFileManagerFactory {
    
    @Override
    public TempFileManager create() {
        return new DefaultTempFileManager();
    }
}
