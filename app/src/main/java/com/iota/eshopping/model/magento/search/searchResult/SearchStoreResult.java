
package com.iota.eshopping.model.magento.search.searchResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;
import com.iota.eshopping.model.modelForView.Tag;

import java.util.List;

/**
 * @author channarith.bong
 */
public class SearchStoreResult extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String storeName;
    @SerializedName("imageUrl")
    @Expose
    private String storeImageUrl;
    @SerializedName("logoUrl")
    @Expose
    private String storeLogoUrl;
    @SerializedName("description")
    @Expose
    private String storeDescription;
    @SerializedName("is_open")
    @Expose
    private boolean isOpen;
    @SerializedName("open_hour")
    @Expose
    private String storeOpenHour;
    @SerializedName("tags")
    @Expose
    private List<Tag> storeTags;
    @SerializedName("percentage")
    @Expose
    private Float storePercentage;
    @SerializedName("rate")
    @Expose
    private Float storeRate;
    @SerializedName("distant")
    @Expose
    private float distant;
    @SerializedName("fee_base_on_distant")
    @Expose
    private float fee;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

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
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @return
     */
    public String getStoreLogoUrl() {
        return storeLogoUrl;
    }

    /**
     * @param storeLogoUrl
     */
    public void setStoreLogoUrl(String storeLogoUrl) {
        this.storeLogoUrl = storeLogoUrl;
    }

    /**
     * @return
     */
    public String getStoreDescription() {
        return storeDescription;
    }

    /**
     * @param storeDescription
     */
    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    /**
     * @return
     */
    public boolean getOpen() {
        return isOpen;
    }

    /**
     * @param open
     */
    public void setOpen(boolean open) {
        isOpen = open;
    }

    /**
     * @return
     */
    public String getStoreOpenHour() {
        return storeOpenHour;
    }

    /**
     * @param storeOpenHour
     */
    public void setStoreOpenHour(String storeOpenHour) {
        this.storeOpenHour = storeOpenHour;
    }

    /**
     * @return
     */
    public List<Tag> getStoreTags() {
        return storeTags;
    }

    /**
     * @param storeTags
     */
    public void setStoreTags(List<Tag> storeTags) {
        this.storeTags = storeTags;
    }

    /**
     * @return
     */
    public Float getStorePercentage() {
        return storePercentage;
    }

    /**
     * @param storePercentage
     */
    public void setStorePercentage(Float storePercentage) {
        this.storePercentage = storePercentage;
    }

    /**
     * @return
     */
    public Float getStoreRate() {
        return storeRate;
    }

    /**
     * @param storeRate
     */
    public void setStoreRate(Float storeRate) {
        this.storeRate = storeRate;
    }

    /**
     * @return
     */
    public String getStoreImageUrl() {
        return storeImageUrl;
    }

    /**
     * @param storeImageUrl
     */
    public void setStoreImageUrl(String storeImageUrl) {
        this.storeImageUrl = storeImageUrl;
    }

    /**
     * @return
     */
    public float getDistant() {
        return distant;
    }

    /**
     * @param distant
     */
    public void setDistant(float distant) {
        this.distant = distant;
    }

    /**
     * @return
     */
    public float getFee() {
        return fee;
    }

    /**
     * @param fee
     */
    public void setFee(float fee) {
        this.fee = fee;
    }

    /**
     * @return
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "SearchStoreResult{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", storeImageUrl='" + storeImageUrl + '\'' +
                ", storeLogoUrl='" + storeLogoUrl + '\'' +
                ", storeDescription='" + storeDescription + '\'' +
                ", isOpen=" + isOpen +
                ", storeOpenHour='" + storeOpenHour + '\'' +
                ", storeTags=" + storeTags +
                ", storePercentage=" + storePercentage +
                ", storeRate=" + storeRate +
                '}';
    }
}
