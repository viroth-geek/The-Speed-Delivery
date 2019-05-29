package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author yeakleang.ay on 7/17/18.
 */

public class OptionProduct extends Entity {

    private Long id;

    @SerializedName("option_id")
    @Expose
    private Long optionId;

    @SerializedName("product_id")
    @Expose
    private Long productId;

    private Long originalProductId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("is_require")
    @Expose
    private Boolean isRequired;

    @SerializedName("sort_order")
    @Expose
    private Integer sortOrder;

    @SerializedName("values")
    @Expose
    private List<OptionValue> optionValues;

    private String productUid;

    /**
     *
     */
    public OptionProduct() {
    }

    /**
     *
     * @param productId Long
     * @param optionId Long
     * @param title String
     * @param optionValues OptionValue
     */
    public OptionProduct(Long productId, Long optionId, String title, List<OptionValue> optionValues) {
        this.productId = productId;
        this.optionId = optionId;
        this.title = title;
        this.optionValues = optionValues;
    }

    /**
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return Long
     */
    public Long getOptionId() {
        return optionId;
    }

    /**
     *
     * @param optionId Long
     */
    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    /**
     *
     * @return Long
     */
    public Long getProductId() {
        return productId;
    }

    /**
     *
     * @param productId Long
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     *
     * @return Long
     */
    public Long getOriginalProductId() {
        return originalProductId;
    }

    /**
     *
     * @param originalProductId Long
     */
    public void setOriginalProductId(Long originalProductId) {
        this.originalProductId = originalProductId;
    }

    /**
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return Boolean
     */
    public Boolean getRequired() {
        return isRequired;
    }

    /**
     *
     * @param required Boolean
     */
    public void setRequired(Boolean required) {
        isRequired = required;
    }

    /**
     *
     * @return Integer
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     *
     * @param sortOrder Integer
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     *
     * @return list of OptionValue
     */
    public List<OptionValue> getOptionValues() {
        return optionValues;
    }

    /**
     *
     * @param optionValues list of OptionValue
     */
    public void setOptionValues(List<OptionValue> optionValues) {
        this.optionValues = optionValues;
    }

    /**
     *
     * @return String
     */
    public String getProductUid() {
        return productUid;
    }

    /**
     *
     * @param productUid String
     */
    public void setProductUid(String productUid) {
        this.productUid = productUid;
    }

    @Override
    public String toString() {
        return "OptionProduct{" +
                "id=" + id +
                ", optionId=" + optionId +
                ", productId=" + productId +
                ", originalProductId=" + originalProductId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", isRequired=" + isRequired +
                ", sortOrder=" + sortOrder +
                ", optionValues=" + optionValues +
                ", productUid='" + productUid + '\'' +
                '}';
    }
}
