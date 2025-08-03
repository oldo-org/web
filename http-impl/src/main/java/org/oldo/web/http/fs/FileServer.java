package org.oldo.web.http.fs;

import org.oldo.web.http.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

import static java.util.Arrays.asList;

public class FileServer implements IServer {
    /**
     * Common mime type for dynamic content: binary
     */
    private static final String MIME_DEFAULT_BINARY = "application/octet-stream";

    /**
     * Default Index file names.
     */
    private final List<String> indexFileNames = new ArrayList<>() {{
        add("index.html");
        add("index.htm");
    }};

    /**
     * Hashtable mapping (String)FILENAME_EXTENSION -> (String)MIME_TYPE
     */
    private final Map<String, String> mimeTypes = new HashMap<>() {{
        put("css", "text/css");
        put("htm", "text/html");
        put("html", "text/html");
        put("xml", "text/xml");
        put("java", "text/x-java-source, text/java");
        put("md", "text/plain");
        put("txt", "text/plain");
        put("asc", "text/plain");
        put("gif", "image/gif");
        put("jpg", "image/jpeg");
        put("jpeg", "image/jpeg");
        put("png", "image/png");
        put("mp3", "audio/mpeg");
        put("m3u", "audio/mpeg-url");
        put("mp4", "video/mp4");
        put("ogv", "video/ogg");
        put("flv", "video/x-flv");
        put("mov", "video/quicktime");
        put("swf", "application/x-shockwave-flash");
        put("js", "application/javascript");
        put("pdf", "application/pdf");
        put("doc", "application/msword");
        put("ogg", "application/x-ogg");
        put("zip", MIME_DEFAULT_BINARY);
        put("exe", MIME_DEFAULT_BINARY);
        put("class", MIME_DEFAULT_BINARY);
    }};

    private final Map<String, FileServerPlugin> mimeTypeHandlers = new HashMap<>();

    private final Iterable<File> rootDirs;
    private final boolean quiet;

    public FileServer(boolean quiet, File... rootDirs) {
        this(quiet, asList(rootDirs));
    }

    public FileServer(boolean quiet, Iterable<File> rootDirs) {
        this.quiet = quiet;
        this.rootDirs = rootDirs;
        init();
    }

    /**
     * Used to initialize and customize the server.
     */
    public void init() {
    }

    public void registerPluginForMimeType(String[] indexFiles, String mimeType,
                                                    FileServerPlugin plugin,
                                                    Map<String, String> commandLineOptions) {
        if (mimeType == null || plugin == null) {
            return;
        }

        if (indexFiles != null) {
            for (String filename : indexFiles) {
                int dot = filename.lastIndexOf('.');
                if (dot >= 0) {
                    String extension = filename.substring(dot + 1).toLowerCase();
                    mimeTypes.put(extension, mimeType);
                }
            }
            indexFileNames.addAll(asList(indexFiles));
        }
        mimeTypeHandlers.put(mimeType, plugin);
        plugin.initialize(commandLineOptions);
    }

    private File getRootDir() {
        return rootDirs.iterator().next();
    }

    private Iterable<File> getRootDirs() {
        return rootDirs;
    }

    @Override
    public IResponse serve(IRequest request) {
        final Map<String, String> headers = request.getHeaders();
        final Map<String, String> params = request.getParms();
        final String uri = request.getUri();

        if (!quiet) {
            System.out.println(request.getMethod() + " '" + uri + "' ");

            for (Entry<String, String> e : headers.entrySet()) {
                System.out.println("  HDR: '" + e.getKey() + "' = '" + e.getValue() + "'");
            }
            for (Entry<String, String> e : params.entrySet()) {
                System.out.println("  PRM: '" + e.getKey() + "' = '" + e.getValue() + "'");
            }
        }

        for (File homeDir : getRootDirs()) {
            // Make sure we won't die of an exception later
            if (!homeDir.isDirectory()) {
                return getInternalErrorResponse("given path is not a directory (" + homeDir + ").");
            }
        }
        return respond(Collections.unmodifiableMap(headers), request, uri);
    }

    private IResponse respond(Map<String, String> headers, IRequest session, final String query) {
        // Remove URL arguments
        final String normalizedQuery = query.trim().replace(File.separatorChar, '/');
        final String uri = normalizedQuery.indexOf('?') >= 0
                ? normalizedQuery.substring(0, normalizedQuery.indexOf('?'))
                : normalizedQuery;

        // Prohibit getting out of current directory
        if (uri.startsWith("src/main") || uri.endsWith("src/main") || uri.contains("../")) {
            return getForbiddenResponse("Won't serve ../ for security reasons.");
        }

        final File homeDir = findHomeDir(uri);
        if (homeDir == null) {
            return getNotFoundResponse();
        }

        // Browsers get confused without '/' after the directory, send a redirect.
        final File f = new File(homeDir, uri);
        if (f.isDirectory() && !uri.endsWith("/")) {
            final String redirectUri = uri + "/";
            final Response res = createResponse(Status.REDIRECT, IServer.MIME_HTML,
                    "<html><body>Redirected: " +
                            "<a href=\"" + redirectUri + "\">" + redirectUri + "</a>" +
                            "</body></html>");
            res.addHeader("Location", redirectUri);
            return res;
        }

        if (f.isDirectory()) {
            // First look for index io (index.html, index.htm, etc) and if none found, list the directory if readable.
            final String indexFile = findIndexFileInDirectory(f);
            if (indexFile == null) {
                if (f.canRead()) {
                    // No index file, list the directory if it is readable
                    return createResponse(Status.OK, IServer.MIME_HTML, DirectoryListing.listDirectory(normalizedQuery, f));
                } else {
                    return getForbiddenResponse("No directory listing.");
                }
            } else {
                return respond(headers, session, normalizedQuery + indexFile);
            }
        }
        final String mimeTypeForFile = getMimeTypeForFile(normalizedQuery);
        final FileServerPlugin plugin = mimeTypeHandlers.get(mimeTypeForFile);
        final IResponse response;
        if (plugin != null) {
            response = plugin.serveFile(normalizedQuery, headers, session, f, mimeTypeForFile);
        } else {
            response = serveFile(headers, f, mimeTypeForFile);
        }
        return response != null ? response : getNotFoundResponse();
    }

    private File findHomeDir(String uri) {
        for (File homeDir : getRootDirs()) {
            if (canServeUri(uri, homeDir)) {
                return homeDir;
            }
        }
        return null;
    }

    private Response getNotFoundResponse() {
        return createResponse(Status.NOT_FOUND, IServer.MIME_PLAINTEXT,
                "Error 404, file not found.");
    }

    private static Response getForbiddenResponse(String s) {
        return createResponse(Status.FORBIDDEN, IServer.MIME_PLAINTEXT, "FORBIDDEN: " + s);
    }

    private Response getInternalErrorResponse(String s) {
        return createResponse(Status.INTERNAL_ERROR, IServer.MIME_PLAINTEXT,
                "INTERNAL ERRROR: " + s);
    }

    private boolean canServeUri(String uri, File homeDir) {
        if (new File(homeDir, uri).exists()) {
            return true;
        } else {
            final String mimeTypeForFile = getMimeTypeForFile(uri);
            final FileServerPlugin plugin = mimeTypeHandlers.get(mimeTypeForFile);
            return plugin != null && plugin.canServeUri(uri, homeDir);
        }
    }

    /**
     * Serves file from homeDir and its' subdirectories (only). Uses only URI,
     * ignores all headers and HTTP parameters.
     */
    private static Response serveFile(Map<String, String> header, File file, String mime) {
        try {
            Response res;
            // Calculate etag
            final String etag = Integer.toHexString(
                    (file.getAbsolutePath() + file.lastModified() + "" + file.length()).hashCode());

            // Support (simple) skipping:
            long startFrom = 0;
            long endAt = -1;
            String range = header.get("range");
            if (range != null) {
                if (range.startsWith("bytes=")) {
                    range = range.substring("bytes=".length());
                    int minus = range.indexOf('-');
                    try {
                        if (minus > 0) {
                            startFrom = Long.parseLong(range.substring(0, minus));
                            endAt = Long.parseLong(range.substring(minus + 1));
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            // Change return code and add Content-Range header when skipping is requested
            long fileLen = file.length();
            if (range != null && startFrom >= 0) {
                if (startFrom >= fileLen) {
                    res = createResponse(Status.RANGE_NOT_SATISFIABLE, IServer.MIME_PLAINTEXT, "");
                    res.addHeader("Content-Range", "bytes 0-0/" + fileLen);
                    res.addHeader("ETag", etag);
                } else {
                    if (endAt < 0) {
                        endAt = fileLen - 1;
                    }
                    long newLen = endAt - startFrom + 1;
                    if (newLen < 0) {
                        newLen = 0;
                    }

                    final long dataLen = newLen;
                    FileInputStream fis = new FileInputStream(file) {
                        @Override
                        public int available() {
                            return (int) dataLen;
                        }
                    };
                    fis.skip(startFrom);

                    res = createResponse(Status.PARTIAL_CONTENT, mime, fis);
                    res.addHeader("Content-Length", "" + dataLen);
                    res.addHeader("Content-Range", "bytes " + startFrom + "-" + endAt + "/" + fileLen);
                    res.addHeader("ETag", etag);
                }
            } else {
                if (etag.equals(header.get("if-none-match")))
                    res = createResponse(Status.NOT_MODIFIED, mime, "");
                else {
                    res = createResponse(Status.OK, mime, new FileInputStream(file));
                    res.addHeader("Content-Length", "" + fileLen);
                    res.addHeader("ETag", etag);
                }
            }
            return res;
            
        } catch (IOException ioe) {
            return getForbiddenResponse("Reading file failed.");
        }
    }

    // Get MIME type from file name extension, if possible
    private String getMimeTypeForFile(String uri) {
        final int dot = uri.lastIndexOf('.');
        return dot >= 0
            ? mimeTypes.get(uri.substring(dot + 1).toLowerCase())
                : MIME_DEFAULT_BINARY;
    }

    // Announce that the file server accepts partial content requests
    private static Response createResponse(Status status, String mimeType, InputStream message) {
        final Response res = new Response(status, mimeType, message);
        res.addHeader("Accept-Ranges", "bytes");
        return res;
    }

    // Announce that the file server accepts partial content requests
    private static Response createResponse(Status status, String mimeType, String message) {
        final Response res = new Response(status, mimeType, message);
        res.addHeader("Accept-Ranges", "bytes");
        return res;
    }

    private String findIndexFileInDirectory(File directory) {
        for (String fileName : indexFileNames) {
            if (new File(directory, fileName).exists()) {
                return fileName;
            }
        }
        return null;
    }

}
