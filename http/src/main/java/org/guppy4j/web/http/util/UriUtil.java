package org.guppy4j.web.http.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * TODO: Document this briefly
 */
public class UriUtil {
    /**
     * URL-encodes everything between "/"-characters. Encodes spaces as '%20' instead of '+'.
     */
    public static String encodeUri(String uri) {
        final StringBuilder newUri = new StringBuilder();
        final StringTokenizer st = new StringTokenizer(uri, "/ ", true);
        while (st.hasMoreTokens()) {
            final String tok = st.nextToken();
            if (tok.equals("/")) {
                newUri.append("/");
            } else if (tok.equals(" ")) {
                newUri.append("%20");
            } else {
                try {
                    newUri.append(URLEncoder.encode(tok, "UTF-8"));
                } catch (UnsupportedEncodingException ignored) {
                }
            }
        }
        return newUri.toString();
    }

    /**
     * Decode percent encoded <code>String</code> values.
     *
     * @param str the percent encoded <code>String</code>
     * @return expanded form of the input, for example "foo%20bar" becomes "foo bar"
     */
    public static String decodePercent(String str) {
        String decoded = null;
        try {
            decoded = URLDecoder.decode(str, "UTF8");
        } catch (UnsupportedEncodingException ignored) {
        }
        return decoded;
    }

    /**
     * Decode parameters from a URL, handing the case where a single parameter name might have been
     * supplied several times, by return lists of values.  In general these lists will contain a single
     * element.
     *
     * @param queryString a query string pulled from the URL.
     * @return a map of <code>String</code> (parameter name) to <code>List&lt;String&gt;</code> (a list of the values supplied).
     */
    public static Map<String, List<String>> decodeParameters(String queryString) {
        Map<String, List<String>> parms = new HashMap<String, List<String>>();
        if (queryString != null) {
            StringTokenizer st = new StringTokenizer(queryString, "&");
            while (st.hasMoreTokens()) {
                String e = st.nextToken();
                int sep = e.indexOf('=');
                String propertyName = (sep >= 0) ? decodePercent(e.substring(0, sep)).trim() : decodePercent(e).trim();
                if (!parms.containsKey(propertyName)) {
                    parms.put(propertyName, new ArrayList<String>());
                }
                String propertyValue = (sep >= 0) ? decodePercent(e.substring(sep + 1)) : null;
                if (propertyValue != null) {
                    parms.get(propertyName).add(propertyValue);
                }
            }
        }
        return parms;
    }
}
