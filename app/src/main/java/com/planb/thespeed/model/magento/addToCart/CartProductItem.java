
package com.planb.thespeed.model.magento.addToCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CartProductItem extends Entity {

    @SerializedName("productId")
    @Expose
    private Long productId;//Request
    @SerializedName("item_id")
    @Expose
    private Long itemId;// Response
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("qty")
    @Expose
    private Long qty;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("quote_id")
    @Expose
    private String quoteId;
    private final static long serialVersionUID = -743293266737422109L;

    /**
     * @return
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * @return
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * @param itemId
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * @return
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return
     */
    public Long getQty() {
        return qty;
    }

    /**
     * @param qty
     */
    public void setQty(Long qty) {
        this.qty = qty;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return
     */
    public String getProductType() {
        return productType;
    }

    /**
     * @param productType
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * @return
     */
    public String getQuoteId() {
        return quoteId;
    }

    /**
     * @param quoteId
     */
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("itemId", itemId).append("sku", sku).append("qty", qty).append("name", name).append("price", price).append("productType", productType).append("quoteId", quoteId).toString();
    }

}
