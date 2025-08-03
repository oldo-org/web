package org.oldo.web.http;

public interface ICookies extends Iterable<String> {

    String read(String name);

    void set(String name, String value, int expires);

    void set(ICookie cookie);

    void delete(String name);

    void unloadQueue(IResponse response);
}
