package com.iota.eshopping.model.form;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 9/25/18.
 */

public class FormGetProductByCategory {

    @SerializedName("categoryId")
    @Expose
    private Long categoryId;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("pageSize")
    @Expose
    private int pageSize;

    /**
     * Get categoryId
     *
     * @return categoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Setter categoryId
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get page
     *
     * @return page
     */
    public int getPage() {
        return page;
    }

    /**
     * Setter page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Get pageSize
     *
     * @return pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Setter pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "FormGetProductByCategory{" +
                "categoryId=" + categoryId +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
