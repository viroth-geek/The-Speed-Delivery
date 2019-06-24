package com.planb.thespeed.model.modelForView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 6/11/18.
 */

public class CreateAddress {

    @SerializedName("address")
    @Expose
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CreateAddress{" +
                "address=" + address +
                '}';
    }
}
