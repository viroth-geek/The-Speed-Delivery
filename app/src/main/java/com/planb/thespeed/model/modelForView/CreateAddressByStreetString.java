package com.planb.thespeed.model.modelForView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.CustomAttribute;
import com.planb.thespeed.model.magento.order.ExtensionAttribute;

import java.util.List;

/**
 * @author yeakleang.ay on 6/11/18.
 */

public class CreateAddressByStreetString {

    @SerializedName("address")
    @Expose
    private AddressByStreetString addressByStreetString;


    public AddressByStreetString getAddressByStreetString() {
        return addressByStreetString;
    }

    public void setAddressByStreetString(AddressByStreetString addressByStreetString) {
        this.addressByStreetString = addressByStreetString;
    }

    @Override
    public String toString() {
        return "CreateAddress{" +
                "address=" + addressByStreetString +
                '}';
    }
}
