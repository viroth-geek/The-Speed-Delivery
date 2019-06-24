package com.planb.thespeed.model.modelForView;

import com.planb.thespeed.model.Entity;

/**
 * @author channarith.bong
 */
public class ItemListAdaptor {
    public static final int HEADER = 0;
    public static final int SECTION_TYPE_PRO = 2;
    public static final int SECTION_TYPE_STO = 1;

    private int type;
    private String tag;
    private Entity data;

    /**
     * @param type
     * @param data
     */
    public ItemListAdaptor(int type, String tag, Entity data) {
        this.type = type;
        this.tag = tag;
        this.data = data;
    }

    /**
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     * @return
     */
    public String getTag() {
        return tag;
    }

    /**
     * @return
     */
    public Entity getData() {
        return data;
    }
}
