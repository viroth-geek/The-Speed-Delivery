package com.planb.thespeed.event;

import com.planb.thespeed.model.modelForView.ProductItem;

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
