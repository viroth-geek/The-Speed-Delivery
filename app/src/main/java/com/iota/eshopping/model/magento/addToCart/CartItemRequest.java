
package com.iota.eshopping.model.magento.addToCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CartItemRequest extends Entity {

    @SerializedName("cartProductItem")
    @Expose
    private CartProductItem cartProductItem;
    private final static long serialVersionUID = -5702502950951129521L;

    /**
     * @return
     */
    public CartProductItem getCartProductItem() {
        return cartProductItem;
    }

    /**
     * @param cartProductItem
     */
    public void setCartProductItem(CartProductItem cartProductItem) {
        this.cartProductItem = cartProductItem;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("cartProductItem", cartProductItem).toString();
    }
}
