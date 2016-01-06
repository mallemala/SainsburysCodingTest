package com.sainsbury.sainsburyhtmlscraper;

/**
 * Main Class
 *
 * @author Dinesh Mallemala
 */
public class Main {

    private static final String WEB_LINK = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";

    public static void main(String[] args) throws Exception {

        HTMLScraper scraper = new HTMLScraper();
        System.out.println(scraper.scrapeProducts(WEB_LINK));
    }
}
