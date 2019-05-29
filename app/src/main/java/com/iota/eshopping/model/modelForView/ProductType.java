package com.iota.eshopping.model.modelForView;

import com.iota.eshopping.model.Entity;

/**
 * @author channarith.bong
 */
public class ProductType extends Entity {

    private Integer id;
    private String name;

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
}
