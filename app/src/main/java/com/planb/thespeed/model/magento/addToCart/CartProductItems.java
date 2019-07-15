package com.planb.thespeed.model.magento.addToCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

import java.util.List;

/**
 * @author yeakleang.ay on 7/17/18.
 */

public class CartProductItems extends Entity {

    @SerializedName("productId")
    @Expose
    private Long productId;

    @SerializedName("qty")
    @Expose
    private Long qty;

    @SerializedName("attributes")
    @Expose
    private List<CartAttribute> cartAttributes;

    @SerializedName("options")
    @Expose
    private List<CartOption> cartOptions;

    /**
     *
     * @return Long
     */
    public Long getProductId() {
        return productId;
    }

    /**
     *
     * @param productId Long
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     *
     * @return Integer
     */
    public Long getQty() {
        return qty;
    }

    /**
     *
     * @param qty Integer
     */
    public void setQty(Long qty) {
        this.qty = qty;
    }

    /**
     *
     * @return List of CartAttribute
     */
    public List<CartAttribute> getCartAttributes() {
        return cartAttributes;
    }

    /**
     *
     * @param cartAttributes List of CartAttribute
     */
    public void setCartAttributes(List<CartAttribute> cartAttributes) {
        this.cartAttributes = cartAttributes;
    }

    /**
     *
     * @return list of CartOption
     */
    public List<CartOption> getCartOptions() {
        return cartOptions;
    }

    /**
     *
     * @param cartOptions list of CartOption
     */
    public void setCartOptions(List<CartOption> cartOptions) {
        this.cartOptions = cartOptions;
    }

    @Override
    public String toString() {
        return "CartProductItems{" +
                "productId=" + productId +
                ", qty=" + qty +
                ", cartAttributes=" + cartAttributes +
                ", cartOptions=" + cartOptions +
                '}';
    }
}
