
package com.planb.thespeed.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.ProductItem;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class ShippingAssignment implements Serializable {

    @SerializedName("shipping")
    @Expose
    private Shipping shipping;
    @SerializedName("items")
    @Expose
    private List<ProductItem> items = null;
    private final static long serialVersionUID = 1883185292814355304L;

    /**
     * @return
     */
    public Shipping getShipping() {
        return shipping;
    }

    /**
     * @param shipping
     */
    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    /**
     * @return
     */
    public List<ProductItem> getItems() {
        return items;
    }

    /**
     * @param items
     */
    public void setItems(List<ProductItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("shipping", shipping).append("items", items).toString();
    }

}
