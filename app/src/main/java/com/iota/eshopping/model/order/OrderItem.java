package com.iota.eshopping.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

/**
 * @author yeakleang.ay on 7/23/18.
 */

public class OrderItem extends Entity {

    @SerializedName("product_id")
    @Expose
    private Long productId;

    @SerializedName("item_id")
    @Expose
    private Long itemId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("sku")
    @Expose
    private String sku;

    @SerializedName("qty")
    @Expose
    private Long qty;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("product_type")
    @Expose
    private String productType;

    @SerializedName("quote_id")
    @Expose
    private Long quoteId;

    @SerializedName("product_option")
    @Expose
    private ProductOption productOption;

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
     * @return Long
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     *
     * @param itemId Long
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return String
     */
    public String getSku() {
        return sku;
    }

    /**
     *
     * @param sku String
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     *
     * @return Long
     */
    public Long getQty() {
        return qty;
    }

    /**
     *
     * @param qty Long
     */
    public void setQty(Long qty) {
        this.qty = qty;
    }

    /**
     *
     * @return Float
     */
    public Double getPrice() {
        return price;
    }

    /**
     *
     * @param price Float
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     *
     * @return String
     */
    public String getProductType() {
        return productType;
    }

    /**
     *
     * @param productType String
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     *
     * @return Long
     */
    public Long getQuoteId() {
        return quoteId;
    }

    /**
     *
     * @param quoteId Long
     */
    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    /**
     * @see ProductOption
     * @return ProductOption
     */
    public ProductOption getProductOption() {
        return productOption;
    }

    /**
     * @see ProductOption
     * @param productOption ProductOption
     */
    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "productId=" + productId +
                ", itemId=" + itemId +
                ", name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", productType='" + productType + '\'' +
                ", quoteId=" + quoteId +
                ", productOption=" + productOption +
                '}';
    }
}
