package org.guppy4j.web.http.server;

import org.guppy4j.web.http.ISession;
import org.guppy4j.web.http.Response;

/**
 * TODO: Document this briefly
 */
public interface IServer {

    Response serve(ISession session);
}
