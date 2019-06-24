package com.planb.thespeed.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 8/22/18.
 */

public class UserPlayerId {

    @SerializedName("customerId")
    @Expose
    private String userId;

    @SerializedName("playerId")
    @Expose
    private String playerId;

    @SerializedName("deviceType")
    @Expose
    private int deviceType;

    /**
     * Get userId
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get playerId
     *
     * @return playerId
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Setter playerId
     */
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * Get deviceType
     *
     * @return deviceType
     */
    public int getDeviceType() {
        return deviceType;
    }

    /**
     * Setter deviceType
     */
    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String toString() {
        return "UserPlayerId{" +
                "userId='" + userId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", deviceType=" + deviceType +
                '}';
    }
}
