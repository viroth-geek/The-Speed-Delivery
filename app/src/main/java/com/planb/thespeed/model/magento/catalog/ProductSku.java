
package com.planb.thespeed.model.magento.catalog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author channarith.bong
 */
public class ProductSku extends Entity {

    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("position")
    @Expose
    private Long position;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    private final static long serialVersionUID = 4080226501826092460L;

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
    public Long getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(Long position) {
        this.position = position;
    }

    /**
     * @return
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("sku", sku).append("position", position).append("categoryId", categoryId).toString();
    }

}
