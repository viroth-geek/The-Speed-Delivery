package com.planb.thespeed.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 7/23/18.
 */

public class ItemOption {

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("value")
    @Expose
    private String value;


    @SerializedName("option_id")
    @Expose
    private Long optionId;

    @SerializedName("option_value")
    @Expose
    private Long optionValue;

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
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value String
     */
    public void setValue(String value) {
        this.value = value;
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
    public Long getOptionValue() {
        return optionValue;
    }

    /**
     *
     * @param optionValue Long
     */
    public void setOptionValue(Long optionValue) {
        this.optionValue = optionValue;
    }

    @Override
    public String toString() {
        return "ItemOption{" +
                "optionId=" + optionId +
                ", optionValue=" + optionValue +
                '}';
    }
}
