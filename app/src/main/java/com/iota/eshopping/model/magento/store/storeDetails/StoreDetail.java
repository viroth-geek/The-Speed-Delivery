
package com.iota.eshopping.model.magento.store.storeDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.CustomAttribute;
import com.iota.eshopping.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class StoreDetail extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("parent_id")
    @Expose
    private Long parentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("position")
    @Expose
    private Long position;
    @SerializedName("level")
    @Expose
    private Long level;
    @SerializedName("children")
    @Expose
    private String children;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("available_sort_by")
    @Expose
    private List<Object> availableSortBy = null;
    @SerializedName("include_in_menu")
    @Expose
    private Boolean includeInMenu;
    @SerializedName("custom_attributes")
    @Expose
    private List<CustomAttribute> customAttributes = null;
    private final static long serialVersionUID = 4011698779724464085L;

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
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return
     */
    public Long getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(Long position) {
        this.position = position;
    }

    /**
     * @return
     */
    public Long getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public void setLevel(Long level) {
        this.level = level;
    }

    /**
     * @return
     */
    public String getChildren() {
        return children;
    }

    /**
     * @param children
     */
    public void setChildren(String children) {
        this.children = children;
    }

    /**
     * @return
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return
     */
    public List<Object> getAvailableSortBy() {
        return availableSortBy;
    }

    /**
     * @param availableSortBy
     */
    public void setAvailableSortBy(List<Object> availableSortBy) {
        this.availableSortBy = availableSortBy;
    }

    /**
     * @return
     */
    public Boolean getIncludeInMenu() {
        return includeInMenu;
    }

    /**
     * @param includeInMenu
     */
    public void setIncludeInMenu(Boolean includeInMenu) {
        this.includeInMenu = includeInMenu;
    }

    /**
     * @return
     */
    public List<com.iota.eshopping.model.CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    /**
     * @param customAttributes
     */
    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("parentId", parentId).append("name", name).append("isActive", isActive).append("position", position).append("level", level).append("children", children).append("createdAt", createdAt).append("updatedAt", updatedAt).append("path", path).append("availableSortBy", availableSortBy).append("includeInMenu", includeInMenu).append("customAttributes", customAttributes).toString();
    }

    /**
     * @param attr
     * @return
     */
    public String getValueOfString(String attr) {
        if (customAttributes != null && attr != null) {
            for (CustomAttribute attribute : customAttributes) {
                if (attribute.getAttributeCode().equals(attr.trim().toLowerCase())) {
                    if (attribute.getValue() instanceof String) {
                        return (String) attribute.getValue();
                    }
                }
            }
        }
        return "";
    }
}
