package com.planb.thespeed.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author yeakleang.ay
 */

public class ProductOptionBody extends Entity {

    @SerializedName("productId")
    @Expose
    private Long productId;

    @SerializedName("attributes")
    @Expose
    private List<Attribute> options;

    /**
     *
     * @return Long
     */
    public Long getProductId() {
        return productId;
    }

    /**
     *
     * @param productId Long
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     *
     * @return list of Attribute
     */
    public List<Attribute> getOptions() {
        return options;
    }

    /**
     *
     * @param options list of Attribute
     */
    public void setOptions(List<Attribute> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "ProductOptionBody{" +
                "productId=" + productId +
                ", options=" + options +
                '}';
    }
}
