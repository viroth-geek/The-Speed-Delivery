package com.planb.thespeed.model.magento.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NokorGroup on 6/8/18.
 */

public class StoreFee {

    @SerializedName("storeId")
    @Expose
    private Long storeId;

    @SerializedName("subTotal")
    @Expose
    private Float total;

    public StoreFee(Long storeId, Float total) {
        this.storeId = storeId;
        this.total = total;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
