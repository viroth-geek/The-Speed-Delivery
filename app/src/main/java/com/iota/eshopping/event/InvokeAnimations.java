package com.iota.eshopping.event;

import com.iota.eshopping.model.modelForView.ProductItem;

import java.util.List;

/**
 * @author channarith.bong
 */
public interface InvokeAnimations {

    /**
     *
     * @param productItems list of ProductItem
     */
    void invokeAnimationByItems(List<ProductItem> productItems);
}
