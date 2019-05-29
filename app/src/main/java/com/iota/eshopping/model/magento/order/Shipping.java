
package com.iota.eshopping.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Address;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Shipping implements Serializable {
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("total")
    @Expose
    private Total total;
    private final static long serialVersionUID = -3767615674902498075L;

    /**
     * @return
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return
     */
    public Total getTotal() {
        return total;
    }

    /**
     * @param total
     */
    public void setTotal(Total total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("address", address).append("method", method).append("total", total).toString();
    }

}
