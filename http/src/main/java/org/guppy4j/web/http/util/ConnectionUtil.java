package org.guppy4j.web.http.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * TODO: Document this!
 */
public class ConnectionUtil {

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
