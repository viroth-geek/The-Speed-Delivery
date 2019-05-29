package com.iota.eshopping.event;

import com.iota.eshopping.model.modelForView.ProductItem;

import java.util.List;

/**
 * @author channarith.bong
 */
public interface OnSelectItemFromBasket {

    /**
     *
     * @param productItem list of ProductItem
     */
    void onSelect(List<ProductItem> productItem);
}
