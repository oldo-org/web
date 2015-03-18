package org.guppy4j.http.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Closes connections and similar objects
 */
public final class ConnectionUtil {

    private ConnectionUtil() {
        // no instances
    }

    public static void close(Closeable... closeables) {
        for (Closeable c : closeables) {
            if (c != null) {
                try {
                    c.close();
                } catch (IOException e) {
                    // nothing we can do
                }
            }
        }
    }

}
