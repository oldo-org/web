package org.guppy4j.http.fs;

import org.guppy4j.http.ServerDaemon;
import org.guppy4j.http.util.ServerRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Start a FileServer in main(..) method
 */
public abstract class FileServerMain {

    private FileServerMain() {
        // no instances
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

        final FileServer fileServer = new FileServer(quiet, rootDirs);

        final ServiceLoader<FileServerPluginInfo> serviceLoader =
            ServiceLoader.load(FileServerPluginInfo.class);

        for (FileServerPluginInfo info : serviceLoader) {
            for (String mime : info.getMimeTypes()) {
                final String[] indexFiles = info.getIndexFilesForMimeType(mime);
                if (!quiet) {
                    System.out.print("# Found plugin for Mime type: \"" + mime + "\"");
                    if (indexFiles != null) {
                        System.out.print(" (serving index io: ");
                        for (String indexFile : indexFiles) {
                            System.out.print(indexFile + " ");
                        }
                    }
                    System.out.println(").");
                }
                fileServer.registerPluginForMimeType(indexFiles, mime, info.getWebServerPlugin(mime), options);
            }
        }

        ServerRunner.executeInstance(new ServerDaemon(host, port, fileServer));
    }
}
