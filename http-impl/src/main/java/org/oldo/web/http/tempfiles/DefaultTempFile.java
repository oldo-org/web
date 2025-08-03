package org.oldo.web.http.tempfiles;

import org.oldo.web.http.util.ConnectionUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
    public OutputStream open() {
        return stream;
    }

    @Override
    public boolean delete() {
        ConnectionUtil.close(stream);
        return file.delete();
    }

    @Override
    public String getName() {
        return file.getAbsolutePath();
    }
}
