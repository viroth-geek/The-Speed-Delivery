package com.iota.eshopping.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

/**
 * Created by NokorGroup on 6/5/18.
 */

public class ExtensionAttribute extends Entity {

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    /**
     *
     */
    public ExtensionAttribute() {
    }

    /**
     *
     * @return
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "ExtensionAttribute{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
