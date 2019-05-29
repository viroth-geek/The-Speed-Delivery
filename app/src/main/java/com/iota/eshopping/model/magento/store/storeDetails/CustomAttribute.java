
package com.iota.eshopping.model.magento.store.storeDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class CustomAttribute implements Serializable {

    @SerializedName("attribute_code")
    @Expose
    private String attributeCode;
    @SerializedName("value")
    @Expose
    private Object value;
    private final static long serialVersionUID = 3534707306012160928L;

    /**
     * @return
     */
    public String getAttributeCode() {
        return attributeCode;
    }

    /**
     * @param attributeCode
     */
    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
    }

    /**
     * @return
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("attributeCode", attributeCode).append("value", value).toString();
    }

}
