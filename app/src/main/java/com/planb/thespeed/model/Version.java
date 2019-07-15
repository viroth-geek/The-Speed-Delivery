package com.planb.thespeed.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 10/1/18.
 */

public class Version extends Entity {

    @SerializedName("update_required")
    @Expose
    private int updateRequired;
    @SerializedName("current_version")
    @Expose
    private String currentVersion;
    @SerializedName("store_url")
    @Expose
    private String storeUrl;

    /**
     * Get updateRequired
     *
     * @return updateRequired
     */
    public int getUpdateRequired() {
        return updateRequired;
    }

    /**
     * Setter updateRequired
     */
    public void setUpdateRequired(int updateRequired) {
        this.updateRequired = updateRequired;
    }

    /**
     * Get currentVersion
     *
     * @return currentVersion
     */
    public String getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Setter currentVersion
     */
    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    /**
     * Get storeUrl
     *
     * @return storeUrl
     */
    public String getStoreUrl() {
        return storeUrl;
    }

    /**
     * Setter storeUrl
     */
    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    @Override
    public String toString() {
        return "Version{" +
                "updateRequired=" + updateRequired +
                ", currentVersion='" + currentVersion + '\'' +
                ", storeUrl='" + storeUrl + '\'' +
                '}';
    }
}
