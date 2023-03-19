package org.oldo.http;

import org.oldo.http.ICookie;
import org.oldo.http.ICookies;
import org.oldo.http.IResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Provides rudimentary support for cookies.
 * Doesn't support 'path', 'secure' nor 'httpOnly'.
 * Feel free to improve it and/or add unsupported features.
 *
 * @author LordFokas
 */
public class Cookies implements ICookies {

    private final HashMap<String, String> cookies = new HashMap<>();
    private final ArrayList<ICookie> queue = new ArrayList<>();

    public Cookies(Map<String, String> httpHeaders) {
        final String raw = httpHeaders.get("cookie");
        if (raw != null) {
            for (String token : raw.split(";")) {
                final String[] data = token.trim().split("=");
                if (data.length == 2) {
                    cookies.put(data[0], data[1]);
                }
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        return cookies.keySet().iterator();
    }

    /**
     * Read a cookie from the HTTP Headers.
     *
     * @param name The cookie's name.
     * @return The cookie's value if it exists, null otherwise.
     */
    @Override
    public String read(String name) {
        return cookies.get(name);
    }

    /**
     * Sets a cookie.
     *
     * @param name    The cookie's name.
     * @param value   The cookie's value.
     * @param expires How many days until the cookie expires.
     */
    @Override
    public void set(String name, String value, int expires) {
        queue.add(new Cookie(name, value, expires));
    }

    @Override
    public void set(ICookie cookie) {
        queue.add(cookie);
    }

    /**
     * Set a cookie with an expiration date from a month ago, effectively deleting it on the client side.
     *
     * @param name The cookie name.
     */
    @Override
    public void delete(String name) {
        set(name, "-delete-", -30);
    }

    /**
     * Internally used by the webserver to add all queued cookies into the Response's HTTP Headers.
     *
     * @param response The Response object to which headers the queued cookies will be added.
     */
    @Override
    public void unloadQueue(IResponse response) {
        for (ICookie cookie : queue) {
            response.addHeader("Set-Cookie", cookie.getHttpHeader());
        }
    }
}

