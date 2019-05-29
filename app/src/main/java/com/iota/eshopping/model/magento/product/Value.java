
package com.iota.eshopping.model.magento.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author channarith.bong
 */
public class Value extends Entity {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("sort_order")
    @Expose
    private Long sortOrder;
    @SerializedName("price")
    @Expose
    private Object price;
    @SerializedName("price_type")
    @Expose
    private Object priceType;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("option_type_id")
    @Expose
    private Long optionTypeId;
    private final static long serialVersionUID = 7133320746077754124L;

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public Long getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder
     */
    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return
     */
    public Object getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Object price) {
        this.price = price;
    }

    /**
     * @return
     */
    public Object getPriceType() {
        return priceType;
    }

    /**
     * @param priceType
     */
    public void setPriceType(Object priceType) {
        this.priceType = priceType;
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
    public Long getOptionTypeId() {
        return optionTypeId;
    }

    /**
     * @param optionTypeId
     */
    public void setOptionTypeId(Long optionTypeId) {
        this.optionTypeId = optionTypeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("title", title).append("sortOrder", sortOrder).append("price", price).append("priceType", priceType).append("sku", sku).append("optionTypeId", optionTypeId).toString();
    }

}
