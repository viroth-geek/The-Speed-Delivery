
package com.planb.thespeed.model.magento.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author channarith.bong
 */
public class CategoryLink extends Entity {

    @SerializedName("position")
    @Expose
    private Long position;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    private final static long serialVersionUID = -7287480902576218462L;

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("position", position).append("categoryId", categoryId).toString();
    }

}
