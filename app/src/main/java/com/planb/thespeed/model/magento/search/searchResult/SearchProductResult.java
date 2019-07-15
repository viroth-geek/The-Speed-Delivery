
package com.planb.thespeed.model.magento.search.searchResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

import java.util.List;

/**
 * @author channarith.bong
 */
public class SearchProductResult extends Entity {

    @SerializedName("product_id")
    @Expose
    private Long id;
    @SerializedName("product_name")
    @Expose
    private String name;
    @SerializedName("product_image_url")
    @Expose
    private String imagUrl;
    @SerializedName("store")
    @Expose
    private List<SearchStoreResult> store;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;

    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
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
    public String getImagUrl() {
        return imagUrl;
    }

    /**
     * @param imagUrl
     */
    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
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

    /**
     * @return
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return
     */
    public List<SearchStoreResult> getStores() {
        return store;
    }

    /**
     * @param stores
     */
    public void setStores(List<SearchStoreResult> stores) {
        this.store = stores;
    }

    @Override
    public String toString() {
        return "SearchProductResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagUrl='" + imagUrl + '\'' +
                ", store=" + store +
                ", categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
