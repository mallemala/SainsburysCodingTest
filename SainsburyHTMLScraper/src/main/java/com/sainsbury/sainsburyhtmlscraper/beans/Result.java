/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sainsbury.sainsburyhtmlscraper.beans;

import com.sainsbury.sainsburyhtmlscraper.beans.Product;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author Dinesh Mallemala
 */
public class Result {

    @SerializedName("results")
    private final List<Product> products;

    @SerializedName("total")
    private final Double total;

    Result(List<Product> products, double total) {
        this.products = products;
        this.total = total;
    }
}
