package com.planb.thespeed.model.modelForView;

import com.planb.thespeed.model.Entity;

import java.util.List;

/**
 * @author channarith.bong
 */
public class ProductCategory extends Entity {

    private Long id;
    private String name;
    private List<Product> products;
    private int count;

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
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Get count
     *
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * Setter count
     */
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + products +
                '}';
    }
}
