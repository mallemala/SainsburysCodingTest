/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sainsbury.sainsburyhtmlscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.junit.Assert;

/**
 *
 * @author Dinesh Mallemala
 */
public class HTMLScraperTest {

    private List<Product> products;
    Product product1;
    private double total;
    private HTMLScraper scraper;
    private Document doc;
    private Elements elements;
    private Element element;
    private Element linkelement;

    public HTMLScraperTest() {

        products = new ArrayList<>();

        product1 = new Product();
        product1.setProductName("Test Product Name 1");
        product1.setHtmlPageSize("20kb");
        product1.setUnitPrice(2.00);
        product1.setProductDescription("Test Product Description 1");

        scraper = EasyMock.createMock(HTMLScraper.class);
        doc = EasyMock.createMock(Document.class);

        element = EasyMock.createMock(Element.class);
        linkelement = EasyMock.createMock(Element.class);
        List<Element> elems = new ArrayList<>();
        elems.add(element);

        elements = new Elements(elems);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testResult() throws IOException {
        String link = "test link";

        EasyMock.expect(scraper.getHTMLDocument(link)).andReturn(doc);
       
        EasyMock.expect(doc.getElementsByClass("productLister")).andReturn(elements);
//        EasyMock.expect(elements.select("li")).andReturn(elements);

        //EasyMock.expect(element.getElementsByClass("productInfo").select("a[href]").first()).andReturn(linkelement);
        EasyMock.expect(element.getElementsByClass("productInfo")).andReturn(elements);
//        EasyMock.expect(elements.select("a[href]")).andReturn(elements);
//        EasyMock.expect(elements.first()).andReturn(linkelement);
        
        EasyMock.expect(element.getElementsByClass("pricePerUnit").text()).andReturn("&pound2.00/unit");

        EasyMock.expect(linkelement.attr("abs:href")).andReturn("link");

        EasyMock.expect(linkelement.text()).andReturn("Test Product Name 1");

        EasyMock.expect(scraper.getWebPageSize(link)).andReturn("20kb");
        EasyMock.expect(scraper.getProductDescription(link)).andReturn("Test Product Description 1");

        EasyMock.replay();

        Assert.assertEquals(scraper.getProductFromElement(linkelement), product1);

        Assert.assertTrue(false);
    }
}
