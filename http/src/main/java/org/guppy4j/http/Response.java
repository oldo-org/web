package org.guppy4j.http;

import java.io.Writer;

/**
 * A HTTP response
 */
public interface Response {

    Writer writer(String contentType);

    void done();
}
