
package com.planb.thespeed.model.magento.store.storeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;
import com.planb.thespeed.model.magento.search.SearchStoreRestriction;

/**
 * @author channarith.bong
 */
public class StoreRestriction extends Entity {
    @SerializedName("storeRestriction")
    @Expose
    private SearchStoreRestriction storeRestriction;

    /**
     * @return
     */
    public SearchStoreRestriction getStoreRestriction() {
        return storeRestriction;
    }

    /**
     * @param storeRestriction
     */
    public void setStoreRestriction(SearchStoreRestriction storeRestriction) {
        this.storeRestriction = storeRestriction;
    }

    @Override
    public String toString() {
        return "StoreRestriction{" +
                "storeRestriction=" + storeRestriction +
                '}';
    }
}
