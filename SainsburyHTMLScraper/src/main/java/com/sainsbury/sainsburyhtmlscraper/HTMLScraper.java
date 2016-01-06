/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sainsbury.sainsburyhtmlscraper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;

/**
 *
 * @author Dinesh Mallemala
 */
public class HTMLScraper {

    public void scrapeProducts(String weblink) throws Exception {

        Document doc = getHTMLDocument(weblink);
        scrapeProducts(doc);
    }

    private void scrapeProducts(Document doc) throws IOException {
        double total = 0.00;
        List<Product> products = new ArrayList<>();

        Elements h_products = doc.getElementsByClass("productLister").select("li");

        for (Element h_product : h_products) {

            Product product = getProductFromElement(h_product);

            total += product.getUnitPrice();

            products.add(product);
        }

        System.out.println(getResultJson(products, total));
    }

    public Product getProductFromElement(Element h_product) throws IOException {
        Element link = h_product.getElementsByClass("productInfo").select("a[href]").first();
        String productLink = link.attr("abs:href");
        double unit_price = Double.parseDouble(h_product.getElementsByClass("pricePerUnit").text().replace("&pound", "").replace("/unit", ""));

        Product product = new Product();

        product.setProductName(link.text());
        product.setHtmlPageSize(getWebPageSize(productLink));
        product.setProductDescription(getProductDescription(productLink));
        product.setUnitPrice(unit_price);

        return product;

    }

    public Document getHTMLDocument(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        doc.outputSettings().escapeMode(Entities.EscapeMode.extended);

        return doc;
    }

    public String getWebPageSize(String link) throws MalformedURLException, IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(link).openConnection();

        double size = ((double) conn.getContentLength()) / 1024;
        conn.disconnect();

        DecimalFormat formatter = new DecimalFormat(".##");

        return formatter.format(size) + "kb";
    }

    public String getProductDescription(String productLink) throws IOException {
        Document doc = Jsoup.connect(productLink).get();
        Elements h_productDataItems = doc.getElementsByClass("productDataItemHeader").select("h3");

        for (Element h_productDataItem : h_productDataItems) {
            if (h_productDataItem.text().equalsIgnoreCase("Description")) {
                return h_productDataItem.nextElementSibling().text();
            }
        }
        return null;
    }

    public String getResultJson(List<Product> products, double total) {

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Double.class, new CustomDoubleSerializer());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();

        Gson gson = gsonBuilder.create();

        return gson.toJson(new Result(products, total));
    }

}
