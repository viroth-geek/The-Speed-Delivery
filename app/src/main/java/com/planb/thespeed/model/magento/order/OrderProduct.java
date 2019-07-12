package com.planb.thespeed.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Address;
import com.planb.thespeed.model.Entity;
import com.planb.thespeed.model.magento.addToCart.CartProductItem;

import java.util.List;

/**
 * Created by channarith.bong on 2/19/18.
 *
 * @author channarith.bong
 */

public class OrderProduct extends Entity {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("items")
    @Expose
    private List<CartProductItem> cartProductItems;
    @SerializedName("shipping_code")
    @Expose
    private String shippingCode;

    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;

    @SerializedName("delivery_comment")
    @Expose
    private String comment;

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
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

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
    public List<CartProductItem> getCartProductItems() {
        return cartProductItems;
    }

    /**
     * @param cartProductItems
     */
    public void setCartProductItems(List<CartProductItem> cartProductItems) {
        this.cartProductItems = cartProductItems;
    }

    /**
     * @return
     */
    public String getShippingCode() {
        return shippingCode;
    }

    /**
     * @param shippingCode
     */
    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    /**
     *
     * @return
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     *
     * @param deliveryDate
     */
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     *
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", cartProductItems=" + cartProductItems +
                ", shippingCode='" + shippingCode + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}

