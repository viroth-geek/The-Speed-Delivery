package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.magento.addToCart.CartItemsRequest;
import com.planb.thespeed.model.magento.addToCart.ResponseAddToCart;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.cart.CartService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 1/9/18.
 *
 * @author channarith.bong
 */
public class AddMultiItemsToCart extends BaseService<List<ResponseAddToCart>> {
    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public AddMultiItemsToCart(CartItemsRequest itemsToCart, String token, InvokeOnCompleteAsync<List<ResponseAddToCart>> onCompleteAsync) {
        super(onCompleteAsync);
        request(itemsToCart, token);
    }

    /**
     * @param itemsToCart
     * @param token
     */
    private void request(CartItemsRequest itemsToCart, String token) {
        ServiceGenerator
                .createService(CartService.class, token)
                .addNewCartItems(itemsToCart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}