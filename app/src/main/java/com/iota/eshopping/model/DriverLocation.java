package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 9/5/18.
 */

public class DriverLocation {

    @SerializedName("driver_id")
    @Expose
    private Long driverId;

    @SerializedName("order_id")
    @Expose
    private Long orderId;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("created_at")
    @Expose
    private String createAt;

    /**
     * Get driverId
     *
     * @return driverId
     */
    public Long getDriverId() {
        return driverId;
    }

    /**
     * Setter driverId
     */
    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    /**
     * Get orderId
     *
     * @return orderId
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * Setter orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
     * Get createAt
     *
     * @return createAt
     */
    public String getCreateAt() {
        return createAt;
    }

    /**
     * Setter createAt
     */
    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "DriverLocation{" +
                "driverId=" + driverId +
                ", orderId=" + orderId +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", createAt='" + createAt + '\'' +
                '}';
    }
}
