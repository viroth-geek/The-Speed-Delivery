package com.planb.thespeed.event;

import com.planb.thespeed.model.modelForView.ProductItem;

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
