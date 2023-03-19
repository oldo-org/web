package org.oldo.http;

import org.oldo.http.util.ConnectionUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import static java.lang.Math.min;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.TimeZone.getTimeZone;

/**
 * HTTP response. Return one of these from serve().
 */
public final class Response implements IResponse {

    private static final String CR_LF = "\r\n";

    private static final int BUFFER_SIZE = 16 * 1024;

    /**
     * HTTP status code after processing, e.g. "200 OK", HTTP_OK
     */
    private final IStatus status;
    /**
     * MIME type of content, e.g. "text/html"
     */
    private final String mimeType;
    /**
     * Data of the response, may be null.
     */
    private final InputStream data;
    /**
     * Headers for the HTTP response. Use addHeader() to add lines.
     */
    private final Map<String, String> headers = new HashMap<>();
    /**
     * The request method that spawned this response.
     */
    private Method requestMethod;
    /**
     * Use chunkedTransfer
     */
    private boolean chunkedTransfer;

    /**
     * Default constructor: response = HTTP_OK, mime = MIME_HTML and your supplied message
     */
    public Response(String msg) {
        this(Status.OK, IServer.MIME_HTML, msg);
    }

    /**
     * Basic constructor.
     */
    public Response(IStatus status, String mimeType, InputStream data) {
        this.status = status;
        this.mimeType = mimeType;
        this.data = data;
    }

    /**
     * Convenience method that makes an InputStream out of given text.
     */
    public Response(IStatus status, String mimeType, String txt) {
        this(status, mimeType, toStream(txt));
    }

    private static InputStream toStream(String txt) {
        if (txt == null) {
            return null;
        }
        return new ByteArrayInputStream(txt.getBytes(UTF_8));
    }

    /**
     * Adds given line to the header.
     */
    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    /**
     * Sends given response to the socket.
     */
    public void send(OutputStream outputStream) {
        try {
            if (status == null) {
                throw new IllegalStateException("sendResponse(): Status can't be null.");
            }
            final PrintWriter pw = new PrintWriter(outputStream);
            pw.print("HTTP/1.1 " + status.getDescription() + ' ' + CR_LF);

            if (mimeType != null) {
                pw.print("Content-Type: " + mimeType + CR_LF);
            }

            if (headers.get("Date") == null) {
                final DateFormat gmtFrmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                gmtFrmt.setTimeZone(getTimeZone("GMT"));
                pw.print("Date: " + gmtFrmt.format(new Date()) + CR_LF);
            }

            for (Entry<String, String> e : headers.entrySet()) {
                pw.print(e.getKey() + ": " + e.getValue() + CR_LF);
            }

            sendConnectionHeaderIfNotAlreadyPresent(pw, headers);

            if (requestMethod != Method.HEAD && chunkedTransfer) {
                sendAsChunked(outputStream, pw);
            } else {
                final int pending = data != null ? data.available() : 0;
                sendContentLengthHeaderIfNotAlreadyPresent(pw, headers, pending);
                pw.print(CR_LF);
                pw.flush();
                sendAsFixedLength(outputStream, pending);
            }
            outputStream.flush();
        } catch (IOException ioe) {
            // Couldn't write? No can do.
        } finally {
            ConnectionUtil.close(data);
        }
    }

    private static void sendContentLengthHeaderIfNotAlreadyPresent(PrintWriter pw,
                                                                   Map<String, String> header,
                                                                   int size) {
        if (!headerAlreadySent(header, "content-length")) {
            pw.print("Content-Length: " + size + CR_LF);
        }
    }

    private static void sendConnectionHeaderIfNotAlreadyPresent(PrintWriter pw, Map<String, String> header) {
        if (!headerAlreadySent(header, "connection")) {
            pw.print("Connection: keep-alive" + CR_LF);
        }
    }

    private static boolean headerAlreadySent(Map<String, String> header, String name) {
        for (String headerName : header.keySet()) {
            if (headerName.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private void sendAsChunked(OutputStream outputStream, PrintWriter pw) throws IOException {
        pw.print("Transfer-Encoding: chunked" + CR_LF);
        pw.print(CR_LF);
        pw.flush();
        final byte[] crlf = CR_LF.getBytes();
        final byte[] buff = new byte[BUFFER_SIZE];
        int read;
        while ((read = data.read(buff)) > 0) {
            outputStream.write(String.format("%x" + CR_LF, read).getBytes());
            outputStream.write(buff, 0, read);
            outputStream.write(crlf);
        }
        outputStream.write(('0' + CR_LF + CR_LF).getBytes());
    }

    private void sendAsFixedLength(OutputStream outputStream, int pending) throws IOException {
        if (requestMethod != Method.HEAD && data != null) {
            final byte[] buff = new byte[BUFFER_SIZE];
            while (pending > 0) {
                final int read = data.read(buff, 0, min(pending, BUFFER_SIZE));
                if (read <= 0) {
                    return;
                }
                outputStream.write(buff, 0, read);
                pending -= read;
            }
        }
    }

    public IStatus status() {
        return status;
    }

    public String mimeType() {
        return mimeType;
    }

    public InputStream data() {
        return data;
    }


    public Method requestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(Method requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setChunkedTransfer(boolean chunkedTransfer) {
        this.chunkedTransfer = chunkedTransfer;
    }
}
