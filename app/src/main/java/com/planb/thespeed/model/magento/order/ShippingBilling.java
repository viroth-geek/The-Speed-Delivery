package com.planb.thespeed.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

/**
 * Created by channarith.bong on 2/9/18.
 *
 * @author channarith.bong
 */

public class ShippingBilling extends Entity {

    @SerializedName("addressInformation")
    @Expose
    private AddressInformation addressInformation;

    /**
     * @return
     */
    public AddressInformation getAddressInformation() {
        return addressInformation;
    }

    /**
     * @param addressInformation
     */
    public void setAddressInformation(AddressInformation addressInformation) {
        this.addressInformation = addressInformation;
    }
}
