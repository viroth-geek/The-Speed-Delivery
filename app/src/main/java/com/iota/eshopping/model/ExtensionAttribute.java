package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author yeakleang.ay
 */
public class ExtensionAttribute {

    @SerializedName("configurable_product_options")
    @Expose
    private List<ProductOption> productOptions;

    /**
     *
     * @return list of ProductOption
     */
    public List<ProductOption> getProductOptions() {
        return productOptions;
    }

    /**
     *
     * @param productOptions list of ProductOption
     */
    public void setProductOptions(List<ProductOption> productOptions) {
        this.productOptions = productOptions;
    }

    @Override
    public String toString() {
        return "ExtensionAttribute{" +
                "productOptions=" + productOptions +
                '}';
    }
}
