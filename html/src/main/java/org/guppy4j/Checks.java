package org.guppy4j;

import static org.guppy4j.BaseUtil.bothNullOrEqual;
import static org.guppy4j.BaseUtil.not;

/**
 * Some runtime checks (similar to JUnit assertions)
 */
public class Checks {

    public static void requireEqual(Object expected, Object actual, String message) {
        if (not(bothNullOrEqual(expected, actual))) {
            throw new IllegalArgumentException(
                message + " (expected: " + expected + ", actual: " + actual + ")");
        }
    }

}
