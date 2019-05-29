package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 9/5/18.
 */

public class StoreDeliveryFee {

    @SerializedName("distance")
    @Expose
    private Double distance;

    @SerializedName("delivery_fee")
    @Expose
    private Double deliveryFee;

    @SerializedName("shipping_method")
    @Expose
    private String shippingMethod;

    /**
     * Get distance
     *
     * @return distance
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * Setter distance
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     * Get deliveryFee
     *
     * @return deliveryFee
     */
    public Double getDeliveryFee() {
        return deliveryFee;
    }

    /**
     * Setter deliveryFee
     */
    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    /**
     * Get shippingMethod
     *
     * @return shippingMethod
     */
    public String getShippingMethod() {
        return shippingMethod;
    }

    /**
     * Setter shippingMethod
     */
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    @Override
    public String toString() {
        return "StoreDeliveryFee{" +
                "distance=" + distance +
                ", deliveryFee=" + deliveryFee +
                ", shippingMethod='" + shippingMethod + '\'' +
                '}';
    }
}
