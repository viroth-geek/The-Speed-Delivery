package com.planb.thespeed.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author yeakleang.ay on 7/23/18.
 */

public class CustomOption {

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
    private String optionValue;

    @SerializedName("detail")
    @Expose
    private List<OptionDetail> optionDetails;

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
     * @return String
     */
    public String getOptionValue() {
        return optionValue;
    }

    /**
     *
     * @param optionValue String
     */
    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    /**
     * Get optionDetails
     *
     * @return optionDetails
     */
    public List<OptionDetail> getOptionDetails() {
        return optionDetails;
    }

    /**
     * Setter optionDetails
     */
    public void setOptionDetails(List<OptionDetail> optionDetails) {
        this.optionDetails = optionDetails;
    }

    @Override
    public String toString() {
        return "CustomOption{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                ", optionId=" + optionId +
                ", optionValue='" + optionValue + '\'' +
                ", optionDetails=" + optionDetails +
                '}';
    }
}
