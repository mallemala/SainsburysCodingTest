package com.sainsbury.sainsburyhtmlscraper.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

/**
 * HTML Utility class
 *
 * @author Dinesh Mallemala
 */
public final class HTMLUtils {

    public static Document getHTMLDocument(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        doc.outputSettings().escapeMode(Entities.EscapeMode.extended);

        return doc;
    }

    public static String getWebPageSize(String link) throws MalformedURLException, IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(link).openConnection();

        double size = ((double) conn.getContentLength()) / 1024;
        conn.disconnect();

        DecimalFormat formatter = new DecimalFormat(".##");

        return formatter.format(size) + "kb";
    }

}
