package com.iota.eshopping.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

/**
 * Created by channarith.bong on 2/9/18.
 *
 * @author channarith.bong
 */
public class AddressInformation extends Entity {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("shipping_address")
    @Expose
    private ShippingAddress shippingAddress;
    @SerializedName("billing_address")
    @Expose
    private ShippingAddress billingAddress;
    @SerializedName("shipping_carrier_code")
    @Expose
    private String shippingCarrierCode;
    @SerializedName("shipping_method_code")
    @Expose
    private String shippingMethodCode;

    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return
     */
    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    /**
     * @param shippingAddress
     */
    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * @return
     */
    public ShippingAddress getBillingAddress() {
        return billingAddress;
    }

    /**
     * @param billingAddress
     */
    public void setBillingAddress(ShippingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    /**
     * @return
     */
    public String getShippingCarrierCode() {
        return shippingCarrierCode;
    }

    /**
     * @param shippingCarrierCode
     */
    public void setShippingCarrierCode(String shippingCarrierCode) {
        this.shippingCarrierCode = shippingCarrierCode;
    }

    /**
     * @return
     */
    public String getShippingMethodCode() {
        return shippingMethodCode;
    }

    /**
     * @param shippingMethodCode
     */
    public void setShippingMethodCode(String shippingMethodCode) {
        this.shippingMethodCode = shippingMethodCode;
    }
}
