package org.guppy4j.web.http.server;

import org.guppy4j.web.http.ISession;
import org.guppy4j.web.http.Method;
import org.guppy4j.web.http.Response;
import org.guppy4j.web.http.ResponseException;
import org.guppy4j.web.http.Session;
import org.guppy4j.web.http.Status;
import org.guppy4j.web.http.files.DefaultTempFileManagerFactory;
import org.guppy4j.web.http.files.TempFileManager;
import org.guppy4j.web.http.files.TempFileManagerFactory;
import org.guppy4j.web.http.util.UriUtil;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A tiny embeddable HTTP server
 */
public class Server implements IDaemon, IServer {
    /**
     * Maximum time to wait on Socket.getInputStream().read() (in milliseconds)
     * This is required as the Keep-Alive HTTP connections would otherwise
     * block the socket reading thread forever (or as long the browser is open).
     */
    public static final int SOCKET_READ_TIMEOUT = 5000;

    /**
     * Common mime type for dynamic content: plain text
     */
    public static final String MIME_PLAINTEXT = "text/plain";

    /**
     * Common mime type for dynamic content: html
     */
    public static final String MIME_HTML = "text/html";

    /**
     * Pseudo-Parameter to use to store the actual query string in the parameters map for later re-processing.
     */
    private static final String QUERY_STRING_PARAMETER = "NanoHttpd.QUERY_STRING";

    private final String hostname;
    private final int myPort;
    private ServerSocket myServerSocket;
    private Thread myThread;

    private Set<Socket> openConnections = new HashSet<Socket>();

    /**
     * Pluggable strategy for asynchronously executing requests.
     */
    private final AsyncRunner asyncRunner;

    /**
     * Pluggable strategy for creating and cleaning up temporary files.
     */
    private TempFileManagerFactory tempFileManagerFactory;

    /**
     * Constructs an HTTP server on given port.
     */
    public Server(int port) {
        this(null, port);
    }

    /**
     * Constructs an HTTP server on given hostname and port.
     */
    public Server(String hostname, int port) {
        this(hostname, port, new DefaultAsyncRunner());
    }

    public Server(String hostname, int port, AsyncRunner asyncRunner) {
        this.hostname = hostname;
        this.myPort = port;
        this.asyncRunner = asyncRunner;
        setTempFileManagerFactory(new DefaultTempFileManagerFactory());
    }

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // nothing we can do
            }
        }
    }

    /**
     * Start the server.
     *
     * @throws java.io.IOException if the socket is in use.
     */
    @Override
    public void start() throws IOException {
        myServerSocket = new ServerSocket();
        myServerSocket.bind((hostname != null) ? new InetSocketAddress(hostname, myPort) : new InetSocketAddress(myPort));

        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    try {
                        final Socket finalAccept = myServerSocket.accept();
                        registerConnection(finalAccept);
                        finalAccept.setSoTimeout(SOCKET_READ_TIMEOUT);
                        final InputStream inputStream = finalAccept.getInputStream();
                        asyncRunner.exec(new Runnable() {
                            @Override
                            public void run() {
                                OutputStream outputStream = null;
                                try {
                                    outputStream = finalAccept.getOutputStream();
                                    TempFileManager tempFileManager = tempFileManagerFactory.create();
                                    Session session = new Session(tempFileManager, inputStream, outputStream, finalAccept.getInetAddress());
                                    while (!finalAccept.isClosed()) {
                                        session.execute(Server.this);
                                    }
                                } catch (Exception e) {
                                    // When the socket is closed by the client, we throw our own SocketException
                                    // to break the  "keep alive" loop above.
                                    if (!(e instanceof SocketException && "NanoHttpd Shutdown".equals(e.getMessage()))) {
                                        e.printStackTrace();
                                    }
                                } finally {
                                    safeClose(outputStream);
                                    safeClose(inputStream);
                                    safeClose(finalAccept);
                                    unRegisterConnection(finalAccept);
                                }
                            }
                        });
                    } catch (IOException e) {
                    }
                } while (!myServerSocket.isClosed());
            }
        });
        myThread.setDaemon(true);
        myThread.setName("NanoHttpd Main Listener");
        myThread.start();
    }

    /**
     * Stop the server.
     */
    @Override
    public void stop() {
        try {
            safeClose(myServerSocket);
            closeAllConnections();
            if (myThread != null) {
                myThread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers that a new connection has been set up.
     *
     * @param socket the {@link java.net.Socket} for the connection.
     */
    public synchronized void registerConnection(Socket socket) {
        openConnections.add(socket);
    }

    /**
     * Registers that a connection has been closed
     *
     * @param socket the {@link java.net.Socket} for the connection.
     */
    public synchronized void unRegisterConnection(Socket socket) {
        openConnections.remove(socket);
    }

    /**
     * Forcibly closes all connections that are open.
     */
    public synchronized void closeAllConnections() {
        for (Socket socket : openConnections) {
            safeClose(socket);
        }
    }

    public final int getListeningPort() {
        return myServerSocket == null ? -1 : myServerSocket.getLocalPort();
    }

    public final boolean wasStarted() {
        return myServerSocket != null && myThread != null;
    }

    public final boolean isAlive() {
        return wasStarted() && !myServerSocket.isClosed() && myThread.isAlive();
    }

    /**
     * Override this to customize the server.
     * <p>
     * <p>
     * (By default, this delegates to serveFile() and allows directory listing.)
     *
     * @param session The HTTP session
     * @return HTTP response, see class Response for details
     */
    @Override
    public Response serve(ISession session) {
        Map<String, String> files = new HashMap<String, String>();
        Method method = session.getMethod();
        if (Method.PUT.equals(method) || Method.POST.equals(method)) {
            try {
                session.parseBody(files);
            } catch (IOException ioe) {
                return new Response(Status.INTERNAL_ERROR, MIME_PLAINTEXT, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            } catch (ResponseException re) {
                return new Response(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
            }
        }

        Map<String, String> parms = session.getParms();
        parms.put(QUERY_STRING_PARAMETER, session.getQueryParameterString());
        return new Response(Status.NOT_FOUND, MIME_PLAINTEXT, "Not Found");
    }

    /**
     * Decode parameters from a URL, handing the case where a single parameter name might have been
     * supplied several times, by return lists of values.  In general these lists will contain a single
     * element.
     *
     * @param parms original <b>NanoHttpd</b> parameters values, as passed to the <code>serve()</code> method.
     * @return a map of <code>String</code> (parameter name) to <code>List&lt;String&gt;</code> (a list of the values supplied).
     */
    public Map<String, List<String>> decodeParameters(Map<String, String> parms) {
        return UriUtil.decodeParameters(parms.get(QUERY_STRING_PARAMETER));
    }

    // ------------------------------------------------------------------------------- //
    //
    // Threading Strategy.
    //
    // ------------------------------------------------------------------------------- //

    // ------------------------------------------------------------------------------- //
    //
    // Temp file handling strategy.
    //
    // ------------------------------------------------------------------------------- //

    /**
     * Pluggable strategy for creating and cleaning up temporary files.
     *
     * @param tempFileManagerFactory new strategy for handling temp files.
     */
    public void setTempFileManagerFactory(TempFileManagerFactory tempFileManagerFactory) {
        this.tempFileManagerFactory = tempFileManagerFactory;
    }
}
