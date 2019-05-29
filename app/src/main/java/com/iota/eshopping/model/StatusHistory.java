
package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class StatusHistory extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("comment")
    @Expose
    private Object comment;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("entity_id")
    @Expose
    private Long entityId;
    @SerializedName("entity_name")
    @Expose
    private String entityName;
    @SerializedName("is_customer_notified")
    @Expose
    private Long isCustomerNotified;
    @SerializedName("is_visible_on_front")
    @Expose
    private Long isVisibleOnFront;
    @SerializedName("parent_id")
    @Expose
    private Long parentId;
    @SerializedName("status")
    @Expose
    private String status;
    private final static long serialVersionUID = 6796595608545545096L;

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
    public Object getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(Object comment) {
        this.comment = comment;
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
    public Long getEntityId() {
        return entityId;
    }

    /**
     * @param entityId
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * @return
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * @param entityName
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * @return
     */
    public Long getIsCustomerNotified() {
        return isCustomerNotified;
    }

    /**
     * @param isCustomerNotified
     */
    public void setIsCustomerNotified(Long isCustomerNotified) {
        this.isCustomerNotified = isCustomerNotified;
    }

    /**
     * @return
     */
    public Long getIsVisibleOnFront() {
        return isVisibleOnFront;
    }

    /**
     * @param isVisibleOnFront
     */
    public void setIsVisibleOnFront(Long isVisibleOnFront) {
        this.isVisibleOnFront = isVisibleOnFront;
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
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("comment", comment).append("createdAt", createdAt).append("entityId", entityId).append("entityName", entityName).append("isCustomerNotified", isCustomerNotified).append("isVisibleOnFront", isVisibleOnFront).append("parentId", parentId).append("status", status).toString();
    }

}
