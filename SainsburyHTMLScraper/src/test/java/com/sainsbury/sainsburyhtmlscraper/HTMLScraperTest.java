package com.sainsbury.sainsburyhtmlscraper;

import com.sainsbury.sainsburyhtmlscraper.utils.HTMLUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sainsbury.sainsburyhtmlscraper.beans.Product;
import com.sainsbury.sainsburyhtmlscraper.beans.Result;
import com.sainsbury.sainsburyhtmlscraper.utils.CustomDoubleSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Test class to test HTMLScraper
 *
 * @author Dinesh Mallemala
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HTMLScraper.class, HTMLUtils.class, String.class})
public class HTMLScraperTest {

    private final String TEST_LINK = "http://www.sainsburys.co.uk";

    // Mock variables
    private final HTMLScraper scraper;
    private final Document mockDoc;
    private final Elements mockElements;
    private final Elements mockElements1;
    private final Elements mockElements2;
    private final Elements mockElements3;
    private final Element mockElement;
    private final Element mockLinkElement;
    private final Elements mockUnitPriceElement;

    public HTMLScraperTest() throws Exception {

        //Create Mocks
        scraper = PowerMockito.spy(new HTMLScraper());
        mockDoc = PowerMockito.mock(Document.class);

        mockElement = PowerMockito.mock(Element.class);
        mockUnitPriceElement = PowerMockito.mock(Elements.class);
        mockLinkElement = PowerMockito.mock(Element.class);
        List<Element> elems = new ArrayList<>();
        elems.add(mockElement);

        mockElements = new Elements(elems);
        mockElements1 = PowerMockito.mock(Elements.class);
        mockElements2 = PowerMockito.mock(Elements.class);
        mockElements3 = PowerMockito.mock(Elements.class);

    }

    @Before
    public void setUpMocks() throws Exception {

        //Mock public methods
        Mockito.when(mockDoc.getElementsByClass("productLister")).thenReturn(mockElements1);
        Mockito.when(mockElements1.select("li")).thenReturn(mockElements);

        Mockito.when(mockElement.getElementsByClass("productInfo")).thenReturn(mockElements2);
        Mockito.when(mockElements2.select("a[href]")).thenReturn(mockElements3);
        Mockito.when(mockElements3.first()).thenReturn(mockLinkElement);

        Mockito.when(mockElement.getElementsByClass("pricePerUnit")).thenReturn(mockUnitPriceElement);
        Mockito.when(mockUnitPriceElement.text()).thenReturn("&pound2.00/unit");

        Mockito.when(mockLinkElement.attr("abs:href")).thenReturn("link");

        Mockito.when(mockLinkElement.text()).thenReturn("Test Product Name 1");

        //Mock static methods
        PowerMockito.mockStatic(HTMLUtils.class);
        Mockito.when(HTMLUtils.getWebPageSize(anyString())).thenReturn("20kb");
        Mockito.when(HTMLUtils.getHTMLDocument(anyString())).thenReturn(mockDoc);

        //Mock Private method
        PowerMockito.doReturn("Test Product Description 1").when(scraper, method(HTMLScraper.class, "getProductDescription", String.class)).withArguments(anyString());

    }

    @Test
    public void testScrapeProducts() throws IOException, Exception {

        String actualResult = scraper.scrapeProducts(TEST_LINK);
        String expectedResult = getExpectedResultToCheck();

        System.out.println(actualResult);
        System.out.println(expectedResult);

        Assert.assertEquals(expectedResult, actualResult);
    }

    private String getExpectedResultToCheck() {

        Product testProduct = new Product();
        testProduct.setProductName("Test Product Name 1");
        testProduct.setHtmlPageSize("20kb");
        testProduct.setUnitPrice(2.00);
        testProduct.setProductDescription("Test Product Description 1");

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Double.class, new CustomDoubleSerializer());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();

        Gson gson = gsonBuilder.create();
        List<Product> productsList = new ArrayList<>();

        productsList.add(testProduct);

        return gson.toJson(new Result(productsList, 2.00));
    }
}
