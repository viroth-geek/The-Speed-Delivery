package com.iota.eshopping.model.magento.addToCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeakleang.ay on 1/25/19.
 */

public class ResponseAddToCart implements Serializable {

    @SerializedName("sub_total")
    @Expose
    private double subTotal;
    @SerializedName("tax")
    @Expose
    private double tax;
    @SerializedName("items")
    @Expose
    private List<CartProductItem> cartProductItems;

    /**
     * Get subTotal
     *
     * @return subTotal
     */
    public double getSubTotal() {
        return subTotal;
    }

    /**
     * Setter subTotal
     */
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Get tax
     *
     * @return tax
     */
    public double getTax() {
        return tax;
    }

    /**
     * Setter tax
     */
    public void setTax(double tax) {
        this.tax = tax;
    }

    /**
     * Get cartProductItems
     *
     * @return cartProductItems
     */
    public List<CartProductItem> getCartProductItems() {
        return cartProductItems;
    }

    /**
     * Setter cartProductItems
     */
    public void setCartProductItems(List<CartProductItem> cartProductItems) {
        this.cartProductItems = cartProductItems;
    }

    @Override
    public String toString() {
        return "ResponseAddToCart{" +
                "subTotal=" + subTotal +
                ", tax=" + tax +
                ", cartProductItems=" + cartProductItems +
                '}';
    }
}
