package org.guppy4j.web.http;

import org.guppy4j.web.http.io.DefaultTempFileManagerFactory;
import org.guppy4j.web.http.io.TempFileManager;
import org.guppy4j.web.http.io.TempFileManagerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static org.guppy4j.web.http.util.ConnectionUtil.close;

/**
 * A tiny embeddable HTTP server daemon
 */
public final class ServerDaemon implements IDaemon, IServer, IPortListener {

    /**
     * Maximum time to wait on Socket.getInputStream().read() (in milliseconds)
     * This is required as the Keep-Alive HTTP connections would otherwise
     * block the socket reading thread forever (or as long the browser is open).
     */
    private static final int SOCKET_READ_TIMEOUT = 5000;

    private final String hostname;
    private final int port;

    private final RequestExecutor executor;

    private final Connections connections = new Connections();
    private final IServer server;

    private ServerSocket serverSocket;
    private Thread serverThread;

    /**
     * Pluggable strategy for creating and cleaning up temporary io.
     */
    private final TempFileManagerFactory tempFileManagerFactory;

    /**
     * Constructs an HTTP server on given port on localhost.
     */
    public ServerDaemon(int port, IServer server) {
        this(null, port, server);
    }

    /**
     * Constructs an HTTP server on given hostname and port.
     */
    public ServerDaemon(String hostname, int port, IServer server) {
        this(hostname, port, server, new DefaultRequestExecutor());
    }

    public ServerDaemon(String hostname, int port, IServer server, RequestExecutor executor) {
        this.hostname = hostname;
        this.port = port;
        this.executor = executor;
        this.server = server;
        this.tempFileManagerFactory = new DefaultTempFileManagerFactory();
    }

    /**
     * Start the server.
     *
     * @throws java.io.IOException if the socket is in use.
     */
    @Override
    public void start() throws IOException {
        this.serverSocket = bindServerSocket();
        this.serverThread = createServerThread(this::run);
    }

    private void run() {
        do {
            try {
                final Socket socket = acceptSocket(serverSocket);
                final InputStream in = socket.getInputStream();
                executor.execute(() -> {
                    try (OutputStream out = socket.getOutputStream()) {
                        executeSession(socket, in, out);
                    } catch (IOException e) {
                        handle(e);
                    } finally {
                        close(in, socket);
                        connections.remove(socket);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!serverSocket.isClosed());
    }

    private void executeSession(Socket socket, InputStream in, OutputStream out) throws IOException {
        final TempFileManager tempFileManager = tempFileManagerFactory.create();
        final Request session = new Request(tempFileManager, in, out, socket.getInetAddress());
        while (!socket.isClosed()) {
            session.execute(server);
        }
    }

    private void handle(IOException e) {
        // When the socket is closed by the client, we throw our own SocketException
        // to break the  "keep alive" loop above.
        if (!(e instanceof SocketException && "HttpServer Shutdown".equals(e.getMessage()))) {
            e.printStackTrace();
        }
    }

    private Socket acceptSocket(ServerSocket ss) throws IOException {
        final Socket socket = ss.accept();
        connections.add(socket);
        socket.setSoTimeout(SOCKET_READ_TIMEOUT);
        return socket;
    }

    private ServerSocket bindServerSocket() throws IOException {
        final ServerSocket ss = new ServerSocket();
        final InetSocketAddress address = (hostname != null)
            ? new InetSocketAddress(hostname, port)
            : new InetSocketAddress(port);
        ss.bind(address);
        return ss;
    }

    private Thread createServerThread(Runnable runnable) {
        final Thread t = new Thread(runnable);
        t.setDaemon(true);
        t.setName("HttpServer Main Listener");
        t.start();
        return t;
    }


    /**
     * Stop the server.
     */
    @Override
    public void stop() {
        try {
            close(serverSocket);
            connections.closeAll();
            if (serverThread != null) {
                serverThread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final int getListeningPort() {
        return serverSocket == null ? -1 : serverSocket.getLocalPort();
    }

    @Override
    public final boolean wasStarted() {
        return serverSocket != null && serverThread != null;
    }

    @Override
    public final boolean isAlive() {
        return wasStarted() && !serverSocket.isClosed() && serverThread.isAlive();
    }

    /**
     * Override this to customize the server.
     * <p>
     * <p>
     * (By default, this delegates to serveFile() and allows directory listing.)
     *
     * @param request The HTTP session
     * @return HTTP response, see class Response for details
     */
    @Override
    public Response serve(IRequest request) {
        return server.serve(request);
    }

}
