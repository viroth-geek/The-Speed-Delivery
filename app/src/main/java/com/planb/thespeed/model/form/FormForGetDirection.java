package com.planb.thespeed.model.form;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 9/7/18.
 */

public class FormForGetDirection {

    @SerializedName("source_latitude")
    @Expose
    private Double sourceLatitude;
    @SerializedName("source_longitude")
    @Expose
    private Double sourceLongitude;
    @SerializedName("destination_latitude")
    @Expose
    private Double destinationLatitude;
    @SerializedName("destination_longitude")
    @Expose
    private Double destinationLongitude;

    /**
     * Get sourceLatitude
     *
     * @return sourceLatitude
     */
    public Double getSourceLatitude() {
        return sourceLatitude;
    }

    /**
     * Setter sourceLatitude
     */
    public void setSourceLatitude(Double sourceLatitude) {
        this.sourceLatitude = sourceLatitude;
    }

    /**
     * Get sourceLongitude
     *
     * @return sourceLongitude
     */
    public Double getSourceLongitude() {
        return sourceLongitude;
    }

    /**
     * Setter sourceLongitude
     */
    public void setSourceLongitude(Double sourceLongitude) {
        this.sourceLongitude = sourceLongitude;
    }

    /**
     * Get destinationLatitude
     *
     * @return destinationLatitude
     */
    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    /**
     * Setter destinationLatitude
     */
    public void setDestinationLatitude(Double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    /**
     * Get destinationLongitude
     *
     * @return destinationLongitude
     */
    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    /**
     * Setter destinationLongitude
     */
    public void setDestinationLongitude(Double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }
}
