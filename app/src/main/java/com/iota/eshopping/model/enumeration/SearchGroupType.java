package com.iota.eshopping.model.enumeration;

/**
 * Created by channarith.bong on 12/28/17.
 * @author channarith.bong
 */

public enum SearchGroupType {
    SPONSOR("Sponsor"), NEW_ARRIVAL("New Arrival"), RECOMMEND("Recommend"), MOST_ORDER("Most Order"), MOST_LOVE("Most Love"), CATEGORY("Category");

    private String desc;

    SearchGroupType(String desc) {
        this.desc = desc;
    }

    /**
     * Get desc
     *
     * @return desc
     */
    public String getDesc() {
        return desc;
    }
}
