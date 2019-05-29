
package com.iota.eshopping.model.magento.catalog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author channarith.bong
 */
public class Catalog extends Entity {

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
    @SerializedName("product_count")
    @Expose
    private Long productCount;
    @SerializedName("children_data")
    @Expose
    private List<Catalog> childrenData = null;
    private final static long serialVersionUID = 1297987187204102999L;

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
    public Long getProductCount() {
        return productCount;
    }

    /**
     * @param productCount
     */
    public void setProductCount(Long productCount) {
        this.productCount = productCount;
    }

    /**
     * @return
     */
    public List<Catalog> getChildrenData() {
        return childrenData;
    }

    /**
     * @param childrenData
     */
    public void setChildrenData(List<Catalog> childrenData) {
        this.childrenData = childrenData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("parentId", parentId).append("name", name).append("isActive", isActive).append("position", position).append("level", level).append("productCount", productCount).append("childrenData", childrenData).toString();
    }

}
