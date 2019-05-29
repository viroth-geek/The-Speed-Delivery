package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NokorGroup on 6/13/18.
 */

public class Attribute {

    @SerializedName("attribute_id")
    @Expose
    private Long attributeId;

    @SerializedName("value")
    @Expose
    private Long attributeValue;

    /**
     *
     * @param attributeId
     * @param attributeValue
     */
    public Attribute(Long attributeId, Long attributeValue) {
        this.attributeId = attributeId;
        this.attributeValue = attributeValue;
    }

    /**
     *
     * @return
     */
    public Long getAttributeId() {
        return attributeId;
    }

    /**
     *
     * @param attributeId
     */
    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    /**
     *
     * @return
     */
    public Long getAttributeValue() {
        return attributeValue;
    }

    /**
     *
     * @param attributeValue
     */
    public void setAttributeValue(Long attributeValue) {
        this.attributeValue = attributeValue;
    }
}
