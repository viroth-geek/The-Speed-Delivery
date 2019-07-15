package com.planb.thespeed.model.magento.addToCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

/**
 * @author yeakleang.ay on 7/17/18.
 */

public class CartAttribute extends Entity {

    @SerializedName("attribute_id")
    @Expose
    private Long attributeId;

    @SerializedName("value_index")
    @Expose
    private Long valueIndex;

    /**
     *
     * @return Long
     */
    public Long getAttributeId() {
        return attributeId;
    }

    /**
     *
     * @param attributeId Long
     */
    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    /**
     *
     * @return Long
     */
    public Long getValueIndex() {
        return valueIndex;
    }

    /**
     *
     * @param valueIndex Long
     */
    public void setValueIndex(Long valueIndex) {
        this.valueIndex = valueIndex;
    }

    @Override
    public String toString() {
        return "CartAttribute{" +
                "attributeId=" + attributeId +
                ", valueIndex=" + valueIndex +
                '}';
    }
}
