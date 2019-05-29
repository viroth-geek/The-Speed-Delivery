package com.iota.eshopping.model.magento.order;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckoutResponse implements Serializable {
    @SerializedName("payment_methods")
    @Expose
    private List<PaymentMethod> paymentMethods = null;
    @SerializedName("totals")
    @Expose
    private Total totals;

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
    public Total getTotals() {
        return totals;
    }

    /**
     * @param totals
     */
    public void setTotals(Total totals) {
        this.totals = totals;
    }
}
