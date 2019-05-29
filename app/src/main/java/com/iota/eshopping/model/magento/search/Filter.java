
package com.iota.eshopping.model.magento.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Filter extends Entity {

    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("condition_type")
    @Expose
    private String conditionType;
    private final static long serialVersionUID = -1985012788983904205L;

    /**
     * @return
     */
    public String getField() {
        return field;
    }

    /**
     * @param field
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return
     */
    public String getConditionType() {
        return conditionType;
    }

    /**
     * @param conditionType
     */
    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("field", field).append("value", value).append("conditionType", conditionType).toString();
    }

}
