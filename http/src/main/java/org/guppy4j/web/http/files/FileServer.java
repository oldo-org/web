package org.guppy4j.web.http.files;

import org.guppy4j.web.http.ISession;
import org.guppy4j.web.http.InternalRewrite;
import org.guppy4j.web.http.Response;
import org.guppy4j.web.http.Status;
import org.guppy4j.web.http.server.Server;
import org.guppy4j.web.http.server.ServerRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;

import static java.util.Arrays.asList;

public class FileServer extends Server {
    /**
     * Common mime type for dynamic content: binary
     */
    private static final String MIME_DEFAULT_BINARY = "application/octet-stream";

    /**
     * Default Index file names.
     */
    private static final List<String> INDEX_FILE_NAMES = new ArrayList<String>() {{
        add("index.html");
        add("index.htm");
    }};

    /**
     * Hashtable mapping (String)FILENAME_EXTENSION -> (String)MIME_TYPE
     */
    private static final Map<String, String> MIME_TYPES = new HashMap<String, String>() {{
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

    private static Map<String, FileServerPlugin> mimeTypeHandlers = new HashMap<String, FileServerPlugin>();
    private final Iterable<File> rootDirs;
    private final boolean quiet;

    public FileServer(String host, int port, boolean quiet, File... rootDirs) {
        this(host, port, quiet, asList(rootDirs));
    }

    public FileServer(String host, int port, boolean quiet, Iterable<File> rootDirs) {
        super(host, port);
        this.quiet = quiet;
        this.rootDirs = rootDirs;
        init();
    }

    /**
     * Used to initialize and customize the server.
     */
    public void init() {
    }

    /**
     * Starts as a standalone file server and waits for Enter.
     */
    public static void main(String[] args) {
        // Defaults
        int port = 8080;
        String host = "127.0.0.1";

        final List<File> rootDirs = new ArrayList<File>();
        boolean quiet = false;
        final Map<String, String> options = new HashMap<String, String>();

        // Parse command-line, with short and long versions of the options.
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equalsIgnoreCase("-h") || args[i].equalsIgnoreCase("--host")) {
                host = args[i + 1];
            } else if (args[i].equalsIgnoreCase("-p") || args[i].equalsIgnoreCase("--port")) {
                port = Integer.parseInt(args[i + 1]);
            } else if (args[i].equalsIgnoreCase("-q") || args[i].equalsIgnoreCase("--quiet")) {
                quiet = true;
            } else if (args[i].equalsIgnoreCase("-d") || args[i].equalsIgnoreCase("--dir")) {
                rootDirs.add(new File(args[i + 1]).getAbsoluteFile());
            } else if (args[i].startsWith("-X:")) {
                int dot = args[i].indexOf('=');
                if (dot > 0) {
                    String name = args[i].substring(0, dot);
                    String value = args[i].substring(dot + 1, args[i].length());
                    options.put(name, value);
                }
            }
        }

        if (rootDirs.isEmpty()) {
            rootDirs.add(new File(".").getAbsoluteFile());
        }

        options.put("host", host);
        options.put("port", "" + port);
        options.put("quiet", String.valueOf(quiet));
        final StringBuilder sb = new StringBuilder();
        for (File dir : rootDirs) {
            if (sb.length() > 0) {
                sb.append(":");
            }
            try {
                sb.append(dir.getCanonicalPath());
            } catch (IOException ignored) {
            }
        }
        options.put("home", sb.toString());

        final ServiceLoader<FileServerPluginInfo> serviceLoader =
                ServiceLoader.load(FileServerPluginInfo.class);

        for (FileServerPluginInfo info : serviceLoader) {
            for (String mime : info.getMimeTypes()) {
                final String[] indexFiles = info.getIndexFilesForMimeType(mime);
                if (!quiet) {
                    System.out.print("# Found plugin for Mime type: \"" + mime + "\"");
                    if (indexFiles != null) {
                        System.out.print(" (serving index files: ");
                        for (String indexFile : indexFiles) {
                            System.out.print(indexFile + " ");
                        }
                    }
                    System.out.println(").");
                }
                registerPluginForMimeType(indexFiles, mime, info.getWebServerPlugin(mime), options);
            }
        }

        ServerRunner.executeInstance(new FileServer(host, port, quiet, rootDirs));
    }

    protected static void registerPluginForMimeType(String[] indexFiles, String mimeType,
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
                    MIME_TYPES.put(extension, mimeType);
                }
            }
            INDEX_FILE_NAMES.addAll(asList(indexFiles));
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

    public Response serve(ISession session) {
        final Map<String, String> headers = session.getHeaders();
        final Map<String, String> params = session.getParms();
        final String uri = session.getUri();

        if (!quiet) {
            System.out.println(session.getMethod() + " '" + uri + "' ");

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
        return respond(Collections.unmodifiableMap(headers), session, uri);
    }

    private Response respond(Map<String, String> headers, ISession session, final String query) {
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
            final Response res = createResponse(Status.REDIRECT, Server.MIME_HTML,
                    "<html><body>Redirected: " +
                            "<a href=\"" + redirectUri + "\">" + redirectUri + "</a>" +
                            "</body></html>");
            res.addHeader("Location", redirectUri);
            return res;
        }

        if (f.isDirectory()) {
            // First look for index files (index.html, index.htm, etc) and if none found, list the directory if readable.
            final String indexFile = findIndexFileInDirectory(f);
            if (indexFile == null) {
                if (f.canRead()) {
                    // No index file, list the directory if it is readable
                    return createResponse(Status.OK, Server.MIME_HTML, DirectoryListing.listDirectory(normalizedQuery, f));
                } else {
                    return getForbiddenResponse("No directory listing.");
                }
            } else {
                return respond(headers, session, normalizedQuery + indexFile);
            }
        }
        final String mimeTypeForFile = getMimeTypeForFile(normalizedQuery);
        final FileServerPlugin plugin = mimeTypeHandlers.get(mimeTypeForFile);
        final Response response;
        if (plugin != null) {
            response = plugin.serveFile(normalizedQuery, headers, session, f, mimeTypeForFile);
            if (response != null && response instanceof InternalRewrite) {
                InternalRewrite rewrite = (InternalRewrite) response;
                return respond(rewrite.getHeaders(), session, rewrite.getUri());
            }
        } else {
            response = serveFile(normalizedQuery, headers, f, mimeTypeForFile);
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

    protected Response getNotFoundResponse() {
        return createResponse(Status.NOT_FOUND, Server.MIME_PLAINTEXT,
                "Error 404, file not found.");
    }

    protected Response getForbiddenResponse(String s) {
        return createResponse(Status.FORBIDDEN, Server.MIME_PLAINTEXT, "FORBIDDEN: "
                + s);
    }

    protected Response getInternalErrorResponse(String s) {
        return createResponse(Status.INTERNAL_ERROR, Server.MIME_PLAINTEXT,
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
    Response serveFile(String uri, Map<String, String> header, File file, String mime) {
        Response res;
        try {
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
                    res = createResponse(Status.RANGE_NOT_SATISFIABLE, Server.MIME_PLAINTEXT, "");
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
                        public int available() throws IOException {
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
        } catch (IOException ioe) {
            res = getForbiddenResponse("Reading file failed.");
        }
        return res;
    }

    // Get MIME type from file name extension, if possible
    private String getMimeTypeForFile(String uri) {
        final int dot = uri.lastIndexOf('.');
        return dot >= 0
                ? MIME_TYPES.get(uri.substring(dot + 1).toLowerCase())
                : MIME_DEFAULT_BINARY;
    }

    // Announce that the file server accepts partial content requests
    private Response createResponse(Status status, String mimeType, InputStream message) {
        final Response res = new Response(status, mimeType, message);
        res.addHeader("Accept-Ranges", "bytes");
        return res;
    }

    // Announce that the file server accepts partial content requests
    private Response createResponse(Status status, String mimeType, String message) {
        final Response res = new Response(status, mimeType, message);
        res.addHeader("Accept-Ranges", "bytes");
        return res;
    }

    private String findIndexFileInDirectory(File directory) {
        for (String fileName : INDEX_FILE_NAMES) {
            if (new File(directory, fileName).exists()) {
                return fileName;
            }
        }
        return null;
    }

}
