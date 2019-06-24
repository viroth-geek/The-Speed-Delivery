
package com.iota.eshopping.model.magento.store.storeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class ListStore extends Entity implements Serializable {

    @SerializedName("list")
    @Expose
    private List<StoreView> list = null;
    @SerializedName("total_record")
    @Expose
    private Long totalRecord;
    @SerializedName("current_page")
    @Expose
    private Long currentPage;
    @SerializedName("last_page")
    @Expose
    private Long lastPage;
    @SerializedName("is_last_page")
    @Expose
    private boolean isLastPage;
    @SerializedName("number_per_page")
    @Expose
    private Long numberPerPage;
    private final static long serialVersionUID = -8051134761566210606L;

    /**
     * @return
     */
    public List<StoreView> getList() {
        return list;
    }

    /**
     * @param list
     */
    public void setList(List<StoreView> list) {
        this.list = list;
    }

    /**
     * @return
     */
    public Long getTotalRecord() {
        return totalRecord;
    }

    /**
     * @param totalRecord
     */
    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }

    /**
     * @return
     */
    public Long getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage
     */
    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Get lastPage
     *
     * @return lastPage
     */
    public Long getLastPage() {
        return lastPage;
    }

    /**
     * Setter lastPage
     */
    public void setLastPage(Long lastPage) {
        this.lastPage = lastPage;
    }

    /**
     * Get isLastPage
     *
     * @return isLastPage
     */
    public boolean isLastPage() {
        return isLastPage;
    }

    /**
     * Setter isLastPage
     */
    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    /**
     * Get numberPerPage
     *
     * @return numberPerPage
     */
    public Long getNumberPerPage() {
        return numberPerPage;
    }

    /**
     * Setter numberPerPage
     */
    public void setNumberPerPage(Long numberPerPage) {
        this.numberPerPage = numberPerPage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("list", list).append("totalRecord", totalRecord).append("currentPage", currentPage).append("lastPage", lastPage).toString();
    }

}
