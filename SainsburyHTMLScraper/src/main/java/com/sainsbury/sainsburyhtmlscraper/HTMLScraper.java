package com.sainsbury.sainsburyhtmlscraper;

import com.sainsbury.sainsburyhtmlscraper.utils.HTMLUtils;
import com.sainsbury.sainsburyhtmlscraper.beans.Result;
import com.sainsbury.sainsburyhtmlscraper.beans.Product;
import com.sainsbury.sainsburyhtmlscraper.utils.JSONUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * HTML Scraper class to scrape the products from given Sainsbury's products web
 * page.
 *
 * @author Dinesh Mallemala
 */
public class HTMLScraper {

    /**
     * Scrapes the products from the the given web link.
     *
     * @param weblink java.lang.String
     * @return jsonString java.lang.String
     * @throws Exception
     */
    public String scrapeProducts(String weblink) throws Exception {

        Document doc = HTMLUtils.getHTMLDocument(weblink);
        return scrapeProducts(doc);
    }

    /**
     * Scrapes the products from the given HTMLDocument
     *
     * @param h_doc org.jsoup.nodes.Document
     * @return jsonString java.lang.String
     * @throws IOException
     */
    public String scrapeProducts(Document h_doc) throws Exception {
        double total = 0.00;
        List<Product> products = new ArrayList<>();

        Elements h_products = h_doc.getElementsByClass("productLister").select("li");

        for (Element h_product : h_products) {

            Product product = getProductFromElement(h_product);

            total += product.getUnitPrice();

            products.add(product);
        }

        return getResultJson(products, total);
    }

    /**
     * Extract Product details from the given html element
     *
     * @param h_product org.jsoup.nodes.Element
     * @return product com.sainsbury.sainsburyhtmlscraper.beans.Product
     * @throws IOException
     */
    private Product getProductFromElement(Element h_product) throws IOException {
        Element link = h_product.getElementsByClass("productInfo").select("a[href]").first();
        String productLink = link.attr("abs:href");
        double unit_price = Double.parseDouble(h_product.getElementsByClass("pricePerUnit").text().replace("&pound", "").replace("/unit", ""));

        Product product = new Product();

        product.setProductName(link.text());
        product.setHtmlPageSize(HTMLUtils.getWebPageSize(productLink));
        product.setProductDescription(getProductDescription(productLink));
        product.setUnitPrice(unit_price);

        return product;
    }

    /**
     * Extracts product description from the given product detail page.
     *
     * @param productLink java.lang.String
     * @return string java.lang.String
     * @throws IOException
     */
    private String getProductDescription(String productLink) throws IOException {
        Document doc = Jsoup.connect(productLink).get();
        Elements h_productDataItems = doc.getElementsByClass("productDataItemHeader").select("h3");

        for (Element h_productDataItem : h_productDataItems) {
            if (h_productDataItem.text().equalsIgnoreCase("Description")) {
                return h_productDataItem.nextElementSibling().text();
            }
        }
        return null;
    }

    /**
     * Return json string from the products and total amount passed
     *
     * @param products java.util.List
     * @param total double
     * @return string java.lang.String
     */
    private String getResultJson(List<Product> products, double total) {
        return JSONUtils.getJsonString(new Result(products, total));
    }

}
