package com.iota.eshopping.model.magento.search;

/**
 * Created by channarith.bong on 1/30/18.
 */

public class SearchParam {

    private String fieldName;
    private String value;
    private String conditionType;

    public SearchParam(String fieldName, String value, String conditionType) {
        this.fieldName = fieldName;
        this.value = value;
        this.conditionType = conditionType;
    }

    /**
     * @return
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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
        return "SearchParam{" +
                "fieldName='" + fieldName + '\'' +
                ", value='" + value + '\'' +
                ", conditionType='" + conditionType + '\'' +
                '}';
    }
}
