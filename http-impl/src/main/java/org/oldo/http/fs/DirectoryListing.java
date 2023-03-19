package org.oldo.http.fs;

import org.oldo.http.util.UriUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySortedSet;

/**
 * Lists directory content as HTML
 */
public class DirectoryListing {

    static String listDirectory(String uri, File f) {
        final String heading = "Directory " + uri;
        final StringBuilder msg = new StringBuilder("<html><head><title>" + heading + "</title><style><!--\n" +
                "span.dirname { font-weight: bold; }\n" +
                "span.filesize { font-size: 75%; }\n" +
                "// -->\n" +
                "</style>" +
                "</head><body><h1>" + heading + "</h1>");

        String up = null;
        if (uri.length() > 1) {
            String u = uri.substring(0, uri.length() - 1);
            int slash = u.lastIndexOf('/');
            if (slash >= 0 && slash < u.length()) {
                up = uri.substring(0, slash + 1);
            }
        }

        final String[] files = f.list((dir, name) -> new File(dir, name).isFile());
        final SortedSet<String> sortedFiles = files == null ? emptySortedSet() : new TreeSet<>(asList(files));

        final String[] dirs = f.list((dir, name) -> new File(dir, name).isDirectory());
        final SortedSet<String> sortedDirs = dirs == null ? emptySortedSet() : new TreeSet<>(asList(dirs));

        if (up != null || sortedDirs.size() + sortedFiles.size() > 0) {
            msg.append("<ul>");
            if (up != null || sortedDirs.size() > 0) {
                msg.append("<section class=\"directories\">");
                if (up != null) {
                    msg.append("<li><a rel=\"directory\" href=\"").append(up).append("\"><span class=\"dirname\">..</span></a></b></li>");
                }
                for (String directory : sortedDirs) {
                    String dir = directory + "/";
                    msg.append("<li><a rel=\"directory\" href=\"").append(UriUtil.encodeUri(uri + dir)).append("\"><span class=\"dirname\">").append(dir).append("</span></a></b></li>");
                }
                msg.append("</section>");
            }
            if (sortedFiles.size() > 0) {
                msg.append("<section class=\"io\">");
                for (String file : sortedFiles) {
                    msg.append("<li><a href=\"").append(UriUtil.encodeUri(uri + file)).append("\"><span class=\"filename\">").append(file).append("</span></a>");
                    File curFile = new File(f, file);
                    long len = curFile.length();
                    msg.append("&nbsp;<span class=\"filesize\">(");
                    if (len < 1024) {
                        msg.append(len).append(" bytes");
                    } else if (len < 1024 * 1024) {
                        msg.append(len / 1024).append(".").append(len % 1024 / 10 % 100).append(" KB");
                    } else {
                        msg.append(len / (1024 * 1024)).append(".").append(len % (1024 * 1024) / 10 % 100).append(" MB");
                    }
                    msg.append(")</span></li>");
                }
                msg.append("</section>");
            }
            msg.append("</ul>");
        }
        msg.append("</body></html>");
        return msg.toString();
    }
}
