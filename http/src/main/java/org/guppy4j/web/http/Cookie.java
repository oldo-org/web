package org.guppy4j.web.http;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static java.lang.String.format;

/**
 * TODO: Document this briefly
 */
public class Cookie {

    private static final String S_S_EXPIRES_S = "%s=%s; expires=%s";

    private final String name, value, expires;

    public Cookie(String name, String value, String expires) {
        this.name = name;
        this.value = value;
        this.expires = expires;
    }

    public Cookie(String name, String value) {
        this(name, value, 30);
    }

    public Cookie(String name, String value, int numDays) {
        this.name = name;
        this.value = value;
        expires = getHttpTime(numDays);
    }

    public String getHTTPHeader() {
        return format(S_S_EXPIRES_S, name, value, expires);
    }

    private static String getHttpTime(int days) {
        final Calendar calendar = Calendar.getInstance();
        final DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return df.format(calendar.getTime());
    }
}
