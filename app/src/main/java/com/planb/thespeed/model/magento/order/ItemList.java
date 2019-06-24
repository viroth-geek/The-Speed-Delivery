package com.planb.thespeed.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;
import com.planb.thespeed.model.OrderDetail;

import java.util.List;

/**
 * Created by channarith.bong on 1/30/18.
 *
 * @author channarith.bong
 */

public class ItemList extends Entity {
    @SerializedName("items")
    @Expose
    private List<OrderDetail> orderDetail;

    /**
     * @return
     */
    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    /**
     * @param orderDetail
     */
    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
