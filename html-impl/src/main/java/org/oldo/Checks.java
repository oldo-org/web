package org.oldo;

import static org.guppy4j.Booleans.not;
import static org.guppy4j.Objects.bothNullOrEqual;

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
