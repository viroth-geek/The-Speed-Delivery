
package com.iota.eshopping.model.magento.store.views;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

public class StoreView extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("website_id")
    @Expose
    private Long websiteId;
    @SerializedName("store_group_id")
    @Expose
    private Long storeGroupId;
    private final static long serialVersionUID = -4313705429968738903L;

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

    /**
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public Long getWebsiteId() {
        return websiteId;
    }

    /**
     * @param websiteId
     */
    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    /**
     * @return
     */
    public Long getStoreGroupId() {
        return storeGroupId;
    }

    /**
     * @param storeGroupId
     */
    public void setStoreGroupId(Long storeGroupId) {
        this.storeGroupId = storeGroupId;
    }

//    @Override
//    public String toString() {
//        return new ToStringBuilder(this).append("id", id).append("code", code).append("name", name).append("websiteId", websiteId).append("storeGroupId", storeGroupId).toString();
//    }

}
