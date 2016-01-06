/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sainsbury.sainsburyhtmlscraper;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Dinesh Mallemala
 */
public class Product {

    @SerializedName("title")
    private String productName;

    @SerializedName("size")
    private String htmlPageSize;

    @SerializedName("unit_price")
    private Double unitPrice;

    @SerializedName("description")
    private String productDescription;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getHtmlPageSize() {
        return htmlPageSize;
    }

    public void setHtmlPageSize(String htmlPageSize) {
        this.htmlPageSize = htmlPageSize;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

}
