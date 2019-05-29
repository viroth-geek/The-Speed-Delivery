package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NokorGroup on 6/15/18.
 */

public class ExchangeRate {

    @SerializedName("KHR")
    @Expose
    private Integer exchangeKh;

    /**
     *
     * @return
     */
    public Integer getExchangeKh() {
        return exchangeKh;
    }

    /**
     *
     * @param exchangeKh
     */
    public void setExchangeKh(Integer exchangeKh) {
        this.exchangeKh = exchangeKh;
    }
}
