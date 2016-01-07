package org.guppy4j.http;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.LITERAL;
import static java.util.regex.Pattern.compile;

/**
 * Resource path implementation
 */
public final class ResourcePathImpl implements ResourcePath {

    private static final Pattern PATH_SEPARATOR = compile("/", LITERAL);

    private final String path;
    private final String[] pathElements;

    public ResourcePathImpl(String path) {
        this.path = path;
        pathElements = PATH_SEPARATOR.split(path);
    }

    @Override
    public String resourceName() {
        return pathElements.length > 1 ? pathElements[1] : null;
    }

    @Override
    public String toString() {
        return path;
    }
}
