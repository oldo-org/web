package org.guppy4j.web.http.tempfiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.guppy4j.web.http.util.ConnectionUtil.close;

/**
 * Default strategy for creating and cleaning up temporary files.
 * <p>
 * By default, temp files are created by <code>File.createTempFile()</code> in
 * the directory specified.</p>
 */
public class DefaultTempFile implements TempFile {

    private final File file;
    private final OutputStream stream;

    public DefaultTempFile(String tempdir) throws IOException {
        file = File.createTempFile("Daemon-", "", new File(tempdir));
        stream = new FileOutputStream(file);
    }

    @Override
    public OutputStream open() throws IOException {
        return stream;
    }

    @Override
    public boolean delete() throws IOException {
        close(stream);
        return file.delete();
    }

    @Override
    public String getName() {
        return file.getAbsolutePath();
    }
}
