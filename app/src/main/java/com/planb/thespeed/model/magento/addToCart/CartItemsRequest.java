package com.planb.thespeed.model.magento.addToCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Address;
import com.planb.thespeed.model.Entity;

import java.util.List;

/**
 * Created by channarith.bong on 3/6/18.
 *
 * @author channarith.bong
 */
public class CartItemsRequest extends Entity {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("storeId")
    @Expose
    private Long storeId;
    @SerializedName("address")
    @Expose
    private Address address;

    @SerializedName("items")
    @Expose
    private List<CartProductItems> items = null;

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
    public List<CartProductItems> getItems() {
        return items;
    }

    /**
     * @param items
     */
    public void setItems(List<CartProductItems> items) {
        this.items = items;
    }

    /**
     * @return
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * @param storeId
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "CartItemsRequest{" +
                "email='" + email + '\'' +
                ", storeId=" + storeId +
                ", address=" + address +
                ", items=" + items +
                '}';
    }
}
