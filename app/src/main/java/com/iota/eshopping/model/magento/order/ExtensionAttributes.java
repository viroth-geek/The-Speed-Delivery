package com.iota.eshopping.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

import java.util.List;

public class ExtensionAttributes extends Entity {

    @SerializedName("shipping_assignments")
    @Expose
    private List<ShippingAssignment> shippingAssignments = null;

    /**
     * @return
     */
    public List<ShippingAssignment> getShippingAssignments() {
        return shippingAssignments;
    }

    /**
     * @param shippingAssignments
     */
    public void setShippingAssignments(List<ShippingAssignment> shippingAssignments) {
        this.shippingAssignments = shippingAssignments;
    }

}
