package com.planb.thespeed.model.modelForView;

import com.planb.thespeed.model.Entity;

/**
 * @author channarith.bong
 */

public class OrderItem extends Entity {
    private Integer id;
    private String name;
    private String dateOrder;
    private float price;

    /**
     * @param id
     * @param name
     * @param dateOrder
     * @param price
     */
    public OrderItem(Integer id, String name, String dateOrder, float price) {
        this.id = id;
        this.name = name;
        this.dateOrder = dateOrder;
        this.price = price;
    }

    /**
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getDateOrder() {
        return dateOrder;
    }

    /**
     * @param dateOrder
     */
    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    /**
     * @return
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
    }
}
