package com.iota.eshopping.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderCheckout implements Serializable {

    @SerializedName("payment_methods")
    @Expose
    private List<PaymentMethod> paymentMethods = null;
    @SerializedName("totals")
    @Expose
    private Totals totals;
    private final static long serialVersionUID = -937344199730201556L;

    /**
     * @return
     */
    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    /**
     * @param paymentMethods
     */
    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    /**
     * @return
     */
    public Totals getTotals() {
        return totals;
    }

    /**
     * @param totals
     */
    public void setTotals(Totals totals) {
        this.totals = totals;
    }

}
