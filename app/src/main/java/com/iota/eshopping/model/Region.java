
package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Region extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("region_code")
    @Expose
    private String regionCode;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("region_id")
    @Expose
    private Long regionId;
    private final static long serialVersionUID = 4567065093289495700L;

    /**
     * @return
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * @param regionCode
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * @return
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return
     */
    public Long getRegionId() {
        return regionId;
    }

    /**
     * @param regionId
     */
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("regionCode", regionCode).append("region", region).append("regionId", regionId).toString();
    }

}
