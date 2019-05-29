package com.iota.eshopping.model.modelForView;

import com.iota.eshopping.model.Entity;
import com.iota.eshopping.util.NumberUtils;

/**
 * @author channarith.bong
 */

public class ProductItem<T extends Entity> extends Entity {

    private Long id;
    private Integer count = 0;
    private Float unitPrice = 0f;
    private Float countPrice = 0f;
    private T item;

    /**
     * @param id
     * @param item
     */
    public ProductItem(Long id, T item) {
        this.id = id;
        this.item = item;
    }

    /**
     * @param id
     * @param unitPrice
     * @param item
     */
    public ProductItem(Long id, Float unitPrice, T item) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.item = item;
    }

    /**
     *
     * @param id
     * @param count
     * @param unitPrice
     * @param item
     */
    public ProductItem(Long id, Integer count, Float unitPrice, T item) {
        this.id = id;
        this.count = count;
        this.unitPrice = unitPrice;
        this.item = item;
    }

    public ProductItem(Long id, Integer count, Float unitPrice, Float countPrice, T item) {
        this.id = id;
        this.count = count;
        this.unitPrice = unitPrice;
        this.countPrice = countPrice;
        this.item = item;
    }

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
    public T getItem() {
        return item;
    }

    /**
     * @param item
     */
    public void setItem(T item) {
        this.item = item;
    }

    /**
     * @return
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return
     */
    public Float getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice
     */
    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return
     */
    public Float getCountPrice() {
        return count * Float.valueOf(NumberUtils.strMoney(unitPrice));
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "id=" + id +
                ", count=" + count +
                ", unitPrice=" + unitPrice +
                ", countPrice=" + countPrice +
                ", item=" + item +
                '}';
    }
}
