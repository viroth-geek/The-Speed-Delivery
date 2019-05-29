package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 8/21/18.
 */

public class OrderStatusDetail {

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("created_at")
    @Expose
    private String createdDate;

    /**
     * Get comment
     *
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

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
     * Get createdDate
     *
     * @return createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Setter createdDate
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "OrderStatusDetail{" +
                "comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
