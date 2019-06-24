
package com.planb.thespeed.model.magento.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author channarith.bong
 */
public class ExtensionAttributes extends Entity {

    @SerializedName("website_ids")
    @Expose
    private List<Long> websiteIds = null;
    @SerializedName("category_links")
    @Expose
    private List<CategoryLink> categoryLinks = null;
    private final static long serialVersionUID = 4288408111752349436L;

    /**
     * @return
     */
    public List<Long> getWebsiteIds() {
        return websiteIds;
    }

    /**
     * @param websiteIds
     */
    public void setWebsiteIds(List<Long> websiteIds) {
        this.websiteIds = websiteIds;
    }

    /**
     * @return
     */
    public List<CategoryLink> getCategoryLinks() {
        return categoryLinks;
    }

    /**
     * @param categoryLinks
     */
    public void setCategoryLinks(List<CategoryLink> categoryLinks) {
        this.categoryLinks = categoryLinks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("websiteIds", websiteIds).append("categoryLinks", categoryLinks).toString();
    }

}
