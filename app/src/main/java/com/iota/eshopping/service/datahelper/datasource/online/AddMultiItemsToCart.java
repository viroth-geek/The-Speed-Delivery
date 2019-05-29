package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.magento.addToCart.CartItemsRequest;
import com.iota.eshopping.model.magento.addToCart.ResponseAddToCart;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.cart.CartService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

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