
package com.planb.thespeed.model.magento.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author channarith.bong
 */
public class SearchStoreRestriction {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("ids")
    @Expose
    private List<Long> ids;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_recommended")
    @Expose
    private Boolean isRecommended;
    @SerializedName("is_sponsor")
    @Expose
    private Boolean isSponsor;
    @SerializedName("is_new")
    @Expose
    private Boolean isNew;
    @SerializedName("is_most_order")
    @Expose
    private Boolean isMostOrder;
    @SerializedName("is_most_love")
    @Expose
    private Boolean isMostLove;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("deliver_time")
    @Expose
    private String deliverTime;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("store_tages")
    @Expose
    private List<Long> storeTages = null;

    /**
     * Get id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get ids
     *
     * @return ids
     */
    public List<Long> getIds() {
        return ids;
    }

    /**
     * Setter ids
     */
    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    /**
     * Get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get isRecommended
     *
     * @return isRecommended
     */
    public Boolean getIsRecommended() {
        return isRecommended;
    }

    /**
     * Setter isRecommended
     */
    public void setIsRecommended(Boolean recommended) {
        isRecommended = recommended;
    }

    /**
     * Get isSponsor
     *
     * @return isSponsor
     */
    public Boolean getIsSponsor() {
        return isSponsor;
    }

    /**
     * Setter isSponsor
     */
    public void setIsSponsor(Boolean sponsor) {
        isSponsor = sponsor;
    }

    /**
     * Get isNew
     *
     * @return isNew
     */
    public Boolean getIsNew() {
        return isNew;
    }

    /**
     * Setter isNew
     */
    public void setIsNew(Boolean aNew) {
        isNew = aNew;
    }

    /**
     * Get isMostOrder
     *
     * @return isMostOrder
     */
    public Boolean getIsMostOrder() {
        return isMostOrder;
    }

    /**
     * Setter isMostOrder
     */
    public void setIsMostOrder(Boolean mostOrder) {
        isMostOrder = mostOrder;
    }

    /**
     * Get isMostLove
     *
     * @return isMostLove
     */
    public Boolean getIsMostLove() {
        return isMostLove;
    }

    /**
     * Setter isMostLove
     */
    public void setIsMostLove(Boolean mostLove) {
        isMostLove = mostLove;
    }

    /**
     * Get latitude
     *
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Setter latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Get longitude
     *
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Setter longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get deliverTime
     *
     * @return deliverTime
     */
    public String getDeliverTime() {
        return deliverTime;
    }

    /**
     * Setter deliverTime
     */
    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    /**
     * Get page
     *
     * @return page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Setter page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * Get limit
     *
     * @return limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Setter limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * Get storeTages
     *
     * @return storeTages
     */
    public List<Long> getStoreTages() {
        return storeTages;
    }

    /**
     * Setter storeTages
     */
    public void setStoreTages(List<Long> storeTages) {
        this.storeTages = storeTages;
    }

    @Override
    public String toString() {
        return "SearchStoreRestriction{" +
                "id=" + id +
                ", ids=" + ids +
                ", name='" + name + '\'' +
                ", isRecommended=" + isRecommended +
                ", isSponsor=" + isSponsor +
                ", isNew=" + isNew +
                ", isMostOrder=" + isMostOrder +
                ", isMostLove=" + isMostLove +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", deliverTime='" + deliverTime + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                ", storeTages=" + storeTages +
                '}';
    }
}
