package org.guppy4j.web.http.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * TODO: Document this!
 */
public class ConnectionUtil {
    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // nothing we can do
            }
        }
    }
}
