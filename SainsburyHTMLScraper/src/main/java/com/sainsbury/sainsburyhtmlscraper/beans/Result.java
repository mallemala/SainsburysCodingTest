package com.sainsbury.sainsburyhtmlscraper.beans;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Result bean. TThis is used to build the final result json string.
 *
 * @author Dinesh Mallemala
 */
public class Result {

    @SerializedName("results")
    private final List<Product> products;

    @SerializedName("total")
    private final Double total;

    public Result(List<Product> products, double total) {
        this.products = products;
        this.total = total;
    }
}
