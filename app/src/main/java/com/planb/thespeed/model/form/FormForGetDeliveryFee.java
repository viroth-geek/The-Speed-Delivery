package com.planb.thespeed.model.form;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 9/5/18.
 */

public class FormForGetDeliveryFee {

    @SerializedName("storeId")
    @Expose
    private Long storeId;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

    /**
     * Get storeId
     *
     * @return storeId
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * Setter storeId
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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
}
