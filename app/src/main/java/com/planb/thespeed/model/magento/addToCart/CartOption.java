package com.planb.thespeed.model.magento.addToCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

import java.util.List;

/**
 * @author yeakleang.ay on 7/17/18.
 */

public class CartOption extends Entity {

    @SerializedName("option_id")
    @Expose
    private Long optionId;

    @SerializedName("option_type_ids")
    @Expose
    private List<Long> optionTypeIds;

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
    public List<Long> getOptionTypeIds() {
        return optionTypeIds;
    }

    /**
     *
     * @param optionTypeIds Long
     */
    public void setOptionTypeIds(List<Long> optionTypeIds) {
        this.optionTypeIds = optionTypeIds;
    }

    @Override
    public String toString() {
        return "CartOption{" +
                "optionId=" + optionId +
                ", optionTypeIds=" + optionTypeIds +
                '}';
    }
}
