package org.guppy4j.web.http.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A temp file.
 * <p/>
 * <p>Temp io are responsible for managing the actual temporary storage and cleaning
 * themselves up when no longer needed.</p>
 */
public interface TempFile {

    OutputStream open() throws IOException;

    boolean delete() throws IOException;

    String getName();
}
