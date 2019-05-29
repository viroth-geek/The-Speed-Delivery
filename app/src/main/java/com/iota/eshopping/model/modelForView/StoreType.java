package com.iota.eshopping.model.modelForView;

import com.iota.eshopping.model.Entity;

/**
 * @author channarith.bong
 */

public class StoreType extends Entity {

    private Integer id;
    private String typeName;
    private String typeDesc;

    /**
     * @param id
     * @param typeName
     * @param typeDesc
     */
    public StoreType(Integer id, String typeName, String typeDesc) {
        this.id = id;
        this.typeName = typeName;
        this.typeDesc = typeDesc;
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
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return
     */
    public String getTypeDesc() {
        return typeDesc;
    }

    /**
     * @param typeDesc
     */
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}
