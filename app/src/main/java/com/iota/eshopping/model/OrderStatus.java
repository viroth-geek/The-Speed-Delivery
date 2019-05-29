package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author yeakleang.ay on 8/21/18.
 */

public class OrderStatus {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<OrderStatusDetail> orderStatusDetails;

    /**
     * Get status
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get orderStatusDetails
     *
     * @return orderStatusDetails
     */
    public List<OrderStatusDetail> getOrderStatusDetails() {
        return orderStatusDetails;
    }

    /**
     * Setter orderStatusDetails
     */
    public void setOrderStatusDetails(List<OrderStatusDetail> orderStatusDetails) {
        this.orderStatusDetails = orderStatusDetails;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "status='" + status + '\'' +
                ", orderStatusDetails=" + orderStatusDetails +
                '}';
    }
}
