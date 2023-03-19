package org.oldo.http.tempfiles;

import org.oldo.http.tempfiles.TempFile;
import org.oldo.http.tempfiles.TempFiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Default strategy for creating and cleaning up temporary io.
 * <p/>
 * <p></p>This class stores its io in the standard location (that is,
 * wherever <code>java.io.tmpdir</code> points to).  Files are added
 * to an internal list, and deleted when no longer needed (that is,
 * when <code>clear()</code> is invoked at the end of processing a
 * request).</p>
 */
public class DefaultTempFiles implements TempFiles {

    private final String tmpdir;
    private final List<TempFile> files;

    public DefaultTempFiles() {
        tmpdir = System.getProperty("java.io.tmpdir");
        files = new ArrayList<>();
    }

    @Override
    public TempFile createNew() throws Exception {
        final TempFile file = new DefaultTempFile(tmpdir);
        files.add(file);
        return file;
    }

    @Override
    public void clear() {
        for (TempFile file : files) {
            try {
                file.delete();
            } catch (Exception ignored) {
            }
        }
        files.clear();
    }
}
