
package com.iota.eshopping.model.magento.store.groups;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class StoreGroup extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("website_id")
    @Expose
    private Long websiteId;
    @SerializedName("root_category_id")
    @Expose
    private Long rootCategoryId;
    @SerializedName("default_store_id")
    @Expose
    private Long defaultStoreId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    private final static long serialVersionUID = -49882237432115971L;

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
    public Long getRootCategoryId() {
        return rootCategoryId;
    }

    /**
     * @param rootCategoryId
     */
    public void setRootCategoryId(Long rootCategoryId) {
        this.rootCategoryId = rootCategoryId;
    }

    /**
     * @return
     */
    public Long getDefaultStoreId() {
        return defaultStoreId;
    }

    /**
     * @param defaultStoreId
     */
    public void setDefaultStoreId(Long defaultStoreId) {
        this.defaultStoreId = defaultStoreId;
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
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("websiteId", websiteId).append("rootCategoryId", rootCategoryId).append("defaultStoreId", defaultStoreId).append("name", name).append("code", code).toString();
    }

}
