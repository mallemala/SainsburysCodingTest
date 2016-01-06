/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sainsbury.sainsburyhtmlscraper;

/**
 *
 * @author Dinesh Mallemala
 */
public class Main {

    private static final String WEB_LINK = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";

    public static void main(String[] args) throws Exception {

        HTMLScraper scraper = new HTMLScraper();
        scraper.scrapeProducts(WEB_LINK);
    }
}
