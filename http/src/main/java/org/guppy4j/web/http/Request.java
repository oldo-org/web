package org.guppy4j.web.http;

import org.guppy4j.web.http.tempfiles.TempFile;
import org.guppy4j.web.http.tempfiles.TempFiles;
import org.guppy4j.web.http.util.UriUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import static org.guppy4j.web.http.util.ConnectionUtil.close;

/**
 * TODO: Document this briefly
 */
public final class Request implements IRequest {

    public static final int BUFSIZE = 8192;

    private final TempFiles tempFiles;
    private final OutputStream out;
    private final PushbackInputStream in;

    private int splitbyte;
    private int rlen;
    private String uri;
    private Method method;
    private Map<String, String> parms;
    private Map<String, String> headers;
    private Cookies cookies;
    private String queryParameterString;

    public Request(TempFiles tempFiles, InputStream in, OutputStream out) {
        this.tempFiles = tempFiles;
        this.in = new PushbackInputStream(in, BUFSIZE);
        this.out = out;
    }

    public Request(TempFiles tempFiles, InputStream in, OutputStream out, InetAddress inetAddress) {
        this.tempFiles = tempFiles;
        this.in = new PushbackInputStream(in, BUFSIZE);
        this.out = out;
        final String remoteIp = inetAddress.isLoopbackAddress() || inetAddress.isAnyLocalAddress() ? "127.0.0.1" : inetAddress.getHostAddress().toString();
        headers = new HashMap<>();

        headers.put("remote-addr", remoteIp);
        headers.put("http-client-ip", remoteIp);
    }

    @Override
    public void handleBy(IServer server) throws IOException {
        try {
            // Read the first 8192 bytes.
            // The full header should fit in here.
            // Apache's default header limit is 8KB.
            // Do NOT assume that a single read will get the entire header at once!
            splitbyte = 0;
            rlen = 0;
            final byte[] buf = new byte[BUFSIZE];
            int read;
            try {
                read = in.read(buf, 0, BUFSIZE);
            } catch (IOException e) {
                close(in);
                close(out);
                throw new SocketException(IServer.HTTP_SERVER_SHUTDOWN);
            }
            if (read == -1) {
                // socket has been closed
                close(in);
                close(out);
                throw new SocketException(IServer.HTTP_SERVER_SHUTDOWN);
            }
            while (read > 0) {
                rlen += read;
                splitbyte = findHeaderEnd(buf, rlen);
                if (splitbyte > 0)
                    break;
                read = in.read(buf, rlen, BUFSIZE - rlen);
            }

            if (splitbyte < rlen) {
                in.unread(buf, splitbyte, rlen - splitbyte);
            }

            parms = new HashMap<>();
            if (null == headers) {
                headers = new HashMap<>();
            }

            // Create a BufferedReader for parsing the header.
            final BufferedReader hin = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(buf, 0, rlen)));

            // Decode the header into parms and header java properties
            final Map<String, String> pre = new HashMap<>();
            decodeHeader(hin, pre, parms, headers);

            method = Method.lookup(pre.get("method"));
            if (method == null) {
                throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Syntax error.");
            }

            uri = pre.get("uri");

            cookies = new Cookies(headers);

            // Ok, now do the serve()
            Response r = server.serve(this);
            if (r == null) {
                throw new ResponseException(Status.INTERNAL_ERROR, "SERVER INTERNAL ERROR: Serve() returned a null response.");
            } else {
                cookies.unloadQueue(r);
                r.setRequestMethod(method);
                r.send(out);
            }
        } catch (SocketException | SocketTimeoutException e) {
            // throw it out to close socket object (finalAccept)
            throw e;
        } catch (IOException ioe) {
            Response r = new Response(Status.INTERNAL_ERROR, IServer.MIME_PLAINTEXT, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            r.send(out);
            close(out);
        } catch (ResponseException re) {
            Response r = new Response(re.getStatus(), IServer.MIME_PLAINTEXT, re.getMessage());
            r.send(out);
            close(out);
        } finally {
            tempFiles.clear();
        }
    }

    @Override
    public void parseBody(Map<String, String> files) throws IOException, ResponseException {
        try (RandomAccessFile randomAccessFile = getTmpBucket()) {

            long size;
            if (headers.containsKey("content-length")) {
                size = Integer.parseInt(headers.get("content-length"));
            } else if (splitbyte < rlen) {
                size = rlen - splitbyte;
            } else {
                size = 0;
            }

            // Now read all the body and write it to f
            final byte[] buf = new byte[512];
            while (rlen >= 0 && size > 0) {
                rlen = in.read(buf, 0, (int) Math.min(size, 512));
                size -= rlen;
                if (rlen > 0) {
                    randomAccessFile.write(buf, 0, rlen);
                }
            }

            // Get the raw body as a byte []
            final ByteBuffer fbuf = randomAccessFile.getChannel().map(MapMode.READ_ONLY, 0, randomAccessFile.length());
            randomAccessFile.seek(0);

            // If the method is POST, there may be parameters
            // in data section, too, read it:
            if (Method.POST.equals(method)) {
                handlePost(files, randomAccessFile, fbuf);
            } else if (Method.PUT.equals(method)) {
                files.put("content", saveTmpFile(fbuf, 0, fbuf.limit()));
            }
        }
    }

    private void handlePost(Map<String, String> files,
                            RandomAccessFile randomAccessFile,
                            ByteBuffer fbuf) throws IOException, ResponseException {
        String contentType = "";
        final String contentTypeHeader = headers.get("content-type");

        StringTokenizer st = null;
        if (contentTypeHeader != null) {
            st = new StringTokenizer(contentTypeHeader, ",; ");
            if (st.hasMoreTokens()) {
                contentType = st.nextToken();
            }
        }

        // Create a BufferedReader for easily reading it as string.
        final InputStream bin = new FileInputStream(randomAccessFile.getFD());
        try (BufferedReader in = new BufferedReader(new InputStreamReader(bin))) {


            if ("multipart/form-data".equalsIgnoreCase(contentType)) {
                // Handle multipart/form-data
                if (st == null || !st.hasMoreTokens()) {
                    throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary missing. Usage: GET /example/file.html");
                }

                String boundaryStartString = "boundary=";
                int boundaryContentStart = contentTypeHeader.indexOf(boundaryStartString) + boundaryStartString.length();
                String boundary = contentTypeHeader.substring(boundaryContentStart, contentTypeHeader.length());
                if (boundary.startsWith("\"") && boundary.endsWith("\"")) {
                    boundary = boundary.substring(1, boundary.length() - 1);
                }

                decodeMultipartData(boundary, fbuf, in, parms, files);
            } else {
                String postLine = "";
                final StringBuilder postLineBuffer = new StringBuilder();
                final char[] pbuf = new char[512];
                int read = in.read(pbuf);
                while (read >= 0 && !postLine.endsWith("\r\n")) {
                    postLine = String.valueOf(pbuf, 0, read);
                    postLineBuffer.append(postLine);
                    read = in.read(pbuf);
                }
                postLine = postLineBuffer.toString().trim();
                // Handle application/x-www-form-urlencoded
                if ("application/x-www-form-urlencoded".equalsIgnoreCase(contentType)) {
                    decodeParms(postLine, parms);
                } else if (postLine.length() != 0) {
                    // Special case for raw POST data => create a special io entry "postData" with raw content data
                    files.put("postData", postLine);
                }
            }
        }
    }

    /**
     * Decodes the sent headers and loads the data into Key/value pairs
     */
    private void decodeHeader(BufferedReader in, Map<String, String> pre, Map<String, String> parms, Map<String, String> headers)
        throws ResponseException {
        try {
            // Read the request line
            String inLine = in.readLine();
            if (inLine == null) {
                return;
            }

            StringTokenizer st = new StringTokenizer(inLine);
            if (!st.hasMoreTokens()) {
                throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
            }

            pre.put("method", st.nextToken());

            if (!st.hasMoreTokens()) {
                throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
            }

            String uri = st.nextToken();

            // Decode parameters from the URI
            int qmi = uri.indexOf('?');
            if (qmi >= 0) {
                decodeParms(uri.substring(qmi + 1), parms);
                uri = UriUtil.decodePercent(uri.substring(0, qmi));
            } else {
                uri = UriUtil.decodePercent(uri);
            }

            // If there's another token, it's protocol version,
            // followed by HTTP headers. Ignore version but parse headers.
            // NOTE: this now forces header names lowercase since they are
            // case insensitive and vary by client.
            if (st.hasMoreTokens()) {
                String line = in.readLine();
                while (line != null && line.trim().length() > 0) {
                    int p = line.indexOf(':');
                    if (p >= 0)
                        headers.put(line.substring(0, p).trim().toLowerCase(Locale.US), line.substring(p + 1).trim());
                    line = in.readLine();
                }
            }

            pre.put("uri", uri);
        } catch (IOException ioe) {
            throw new ResponseException(Status.INTERNAL_ERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage(), ioe);
        }
    }

    /**
     * Decodes the Multipart Body data and put it into Key/Value pairs.
     */
    private void decodeMultipartData(String boundary, ByteBuffer fbuf, BufferedReader in, Map<String, String> parms,
                                     Map<String, String> files) throws ResponseException {
        try {
            int[] bpositions = getBoundaryPositions(fbuf, boundary.getBytes());
            int boundarycount = 1;
            String mpline = in.readLine();
            while (mpline != null) {
                if (!mpline.contains(boundary)) {
                    throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but next chunk does not start with boundary. Usage: GET /example/file.html");
                }
                boundarycount++;
                Map<String, String> item = new HashMap<String, String>();
                mpline = in.readLine();
                while (mpline != null && mpline.trim().length() > 0) {
                    int p = mpline.indexOf(':');
                    if (p != -1) {
                        item.put(mpline.substring(0, p).trim().toLowerCase(Locale.US), mpline.substring(p + 1).trim());
                    }
                    mpline = in.readLine();
                }
                if (mpline != null) {
                    String contentDisposition = item.get("content-disposition");
                    if (contentDisposition == null) {
                        throw new ResponseException(Status.BAD_REQUEST, "BAD REQUEST: Content type is multipart/form-data but no content-disposition info found. Usage: GET /example/file.html");
                    }
                    StringTokenizer st = new StringTokenizer(contentDisposition, ";");
                    Map<String, String> disposition = new HashMap<String, String>();
                    while (st.hasMoreTokens()) {
                        String token = st.nextToken().trim();
                        int p = token.indexOf('=');
                        if (p != -1) {
                            disposition.put(token.substring(0, p).trim().toLowerCase(Locale.US), token.substring(p + 1).trim());
                        }
                    }
                    String pname = disposition.get("name");
                    pname = pname.substring(1, pname.length() - 1);

                    String value = "";
                    if (item.get("content-type") == null) {
                        while (mpline != null && !mpline.contains(boundary)) {
                            mpline = in.readLine();
                            if (mpline != null) {
                                int d = mpline.indexOf(boundary);
                                if (d == -1) {
                                    value += mpline;
                                } else {
                                    value += mpline.substring(0, d - 2);
                                }
                            }
                        }
                    } else {
                        if (boundarycount > bpositions.length) {
                            throw new ResponseException(Status.INTERNAL_ERROR, "Error processing request");
                        }
                        int offset = stripMultipartHeaders(fbuf, bpositions[boundarycount - 2]);
                        String path = saveTmpFile(fbuf, offset, bpositions[boundarycount - 1] - offset - 4);
                        files.put(pname, path);
                        value = disposition.get("filename");
                        value = value.substring(1, value.length() - 1);
                        do {
                            mpline = in.readLine();
                        } while (mpline != null && !mpline.contains(boundary));
                    }
                    parms.put(pname, value);
                }
            }
        } catch (IOException ioe) {
            throw new ResponseException(Status.INTERNAL_ERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage(), ioe);
        }
    }

    /**
     * Find byte index separating header from body. It must be the last byte of the first two sequential new lines.
     */
    private int findHeaderEnd(final byte[] buf, int rlen) {
        int splitbyte = 0;
        while (splitbyte + 3 < rlen) {
            if (buf[splitbyte] == '\r' && buf[splitbyte + 1] == '\n' && buf[splitbyte + 2] == '\r' && buf[splitbyte + 3] == '\n') {
                return splitbyte + 4;
            }
            splitbyte++;
        }
        return 0;
    }

    /**
     * Find the byte positions where multipart boundaries start.
     */
    private int[] getBoundaryPositions(ByteBuffer b, byte[] boundary) {
        int matchcount = 0;
        int matchbyte = -1;
        List<Integer> matchbytes = new ArrayList<>();
        for (int i = 0; i < b.limit(); i++) {
            if (b.get(i) == boundary[matchcount]) {
                if (matchcount == 0)
                    matchbyte = i;
                matchcount++;
                if (matchcount == boundary.length) {
                    matchbytes.add(matchbyte);
                    matchcount = 0;
                    matchbyte = -1;
                }
            } else {
                i -= matchcount;
                matchcount = 0;
                matchbyte = -1;
            }
        }
        int[] ret = new int[matchbytes.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = matchbytes.get(i);
        }
        return ret;
    }

    /**
     * Retrieves the content of a sent file and saves it to a temporary file. The full path to the saved file is returned.
     */
    private String saveTmpFile(ByteBuffer b, int offset, int len) {
        String path = "";
        if (len > 0) {
            FileOutputStream fileOutputStream = null;
            try {
                TempFile tempFile = tempFiles.createNew();
                ByteBuffer src = b.duplicate();
                fileOutputStream = new FileOutputStream(tempFile.getName());
                FileChannel dest = fileOutputStream.getChannel();
                src.position(offset).limit(offset + len);
                dest.write(src.slice());
                path = tempFile.getName();
            } catch (Exception e) { // Catch exception if any
                throw new Error(e); // we won't recover, so throw an error
            } finally {
                close(fileOutputStream);
            }
        }
        return path;
    }

    private RandomAccessFile getTmpBucket() {
        try {
            TempFile tempFile = tempFiles.createNew();
            return new RandomAccessFile(tempFile.getName(), "rw");
        } catch (Exception e) {
            throw new Error(e); // we won't recover, so throw an error
        }
    }

    /**
     * It returns the offset separating multipart file headers from the file's data.
     */
    private int stripMultipartHeaders(ByteBuffer b, int offset) {
        int i;
        for (i = offset; i < b.limit(); i++) {
            if (b.get(i) == '\r' && b.get(++i) == '\n' && b.get(++i) == '\r' && b.get(++i) == '\n') {
                break;
            }
        }
        return i + 1;
    }

    /**
     * Decodes parameters in percent-encoded URI-format ( e.g. "name=Jack%20Daniels&pass=Single%20Malt" ) and
     * adds them to given Map. NOTE: this doesn't support multiple identical keys due to the simplicity of Map.
     */
    private void decodeParms(String parms, Map<String, String> p) {
        if (parms == null) {
            queryParameterString = "";
            return;
        }

        queryParameterString = parms;
        StringTokenizer st = new StringTokenizer(parms, "&");
        while (st.hasMoreTokens()) {
            String e = st.nextToken();
            int sep = e.indexOf('=');
            if (sep >= 0) {
                p.put(UriUtil.decodePercent(e.substring(0, sep)).trim(),
                    UriUtil.decodePercent(e.substring(sep + 1)));
            } else {
                p.put(UriUtil.decodePercent(e).trim(), "");
            }
        }
    }

    @Override
    public final Map<String, String> getParms() {
        return parms;
    }

    public String getQueryParameterString() {
        return queryParameterString;
    }

    @Override
    public final Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public final String getUri() {
        return uri;
    }

    @Override
    public final Method getMethod() {
        return method;
    }

    @Override
    public final InputStream getInputStream() {
        return in;
    }

    @Override
    public Cookies getCookies() {
        return cookies;
    }
}
