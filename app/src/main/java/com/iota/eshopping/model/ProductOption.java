package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author yeakleang.ay
 */
public class ProductOption extends Entity {

    private Long id;

    @SerializedName("product_id")
    @Expose
    private Long productId;

    private Long originalProductId;

    @SerializedName("attribute_id")
    @Expose
    private String attributeId;

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("position")
    @Expose
    private Integer position;

    @SerializedName("options")
    @Expose
    private List<Option> options;

    private String productUid;

    /**
     *
     */
    public ProductOption() {
    }

    /**
     * @param productId Long
     * @param attributeId String
     * @param label String
     * @param options list of Option
     */
    public ProductOption(Long productId, String attributeId, String label, List<Option> options) {
        this.productId = productId;
        this.attributeId = attributeId;
        this.label = label;
        this.options = options;
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
    public String getAttributeId() {
        return attributeId;
    }

    /**
     *
     * @param attributeId String
     */
    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    /**
     *
     * @return String
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param label String
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     *
     * @return list of Option
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     *
     * @param options list of Option
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }

    /**
     *
     * @return String
     */
    public Integer getPosition() {
        return position;
    }

    /**
     *
     * @param position String
     */
    public void setPosition(Integer position) {
        this.position = position;
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
        return "ProductOption{" +
                "id=" + id +
                ", productId=" + productId +
                ", originalProductId=" + originalProductId +
                ", attributeId='" + attributeId + '\'' +
                ", label='" + label + '\'' +
                ", position=" + position +
                ", options=" + options +
                ", productUid='" + productUid + '\'' +
                '}';
    }
}
