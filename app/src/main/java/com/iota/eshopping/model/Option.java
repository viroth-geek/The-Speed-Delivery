package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay
 */

public class Option extends Entity {

    private Long id;

    private String productUid;

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("value_index")
    @Expose
    private Long valueIndex;

    private boolean isCheck;

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
     * @return String
     */
    public Long getValueIndex() {
        return valueIndex;
    }

    /**
     *
     * @param valueIndex String
     */
    public void setValueIndex(Long valueIndex) {
        this.valueIndex = valueIndex;
    }

    /**
     *
     * @return boolean
     */
    public boolean getCheck() {
        return isCheck;
    }

    /**
     *
     * @param check boolean
     */
    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", productUid='" + productUid + '\'' +
                ", label='" + label + '\'' +
                ", valueIndex=" + valueIndex +
                ", isCheck=" + isCheck +
                '}';
    }
}
