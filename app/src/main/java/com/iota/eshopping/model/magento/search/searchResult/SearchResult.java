
package com.iota.eshopping.model.magento.search.searchResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

import java.util.List;

/**
 * @author channarith.bong
 */
public class SearchResult extends Entity {

    @SerializedName("stores")
    @Expose
    private List<SearchStoreResult> stores = null;
    @SerializedName("products")
    @Expose
    private List<SearchProductResult> products = null;

    /**
     * @return
     */
    public List<SearchStoreResult> getStores() {
        return stores;
    }

    /**
     * @param stores
     */
    public void setStores(List<SearchStoreResult> stores) {
        this.stores = stores;
    }

    /**
     * @return
     */
    public List<SearchProductResult> getProducts() {
        return products;
    }

    /**
     * @param products
     */
    public void setProducts(List<SearchProductResult> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "stores=" + stores +
                ", products=" + products +
                '}';
    }
}
