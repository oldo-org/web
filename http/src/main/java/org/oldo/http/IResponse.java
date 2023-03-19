package org.oldo.http;

import java.io.OutputStream;

/**
 * Created by oliver on 06/01/16.
 */
public interface IResponse {

    void setRequestMethod(Method method);

    void addHeader(String name, String value);

    void send(OutputStream out);
}
