
package com.iota.eshopping.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShippingMethod implements Serializable {

    @SerializedName("address")
    @Expose
    private ShippingAddress address;
    private final static long serialVersionUID = 8411964839901688159L;

    /**
     * @return
     */
    public ShippingAddress getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(ShippingAddress address) {
        this.address = address;
    }
}
