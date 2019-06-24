package com.planb.thespeed.service;

import com.planb.thespeed.model.magento.addToCart.CartItemRequest;
import com.planb.thespeed.model.magento.addToCart.CartProductItem;
import com.planb.thespeed.service.datahelper.datasource.online.AddItemToCart;
import com.planb.thespeed.service.datahelper.datasource.online.GenerateQuote;

/**
 * Created by channarith.bong on 1/9/18.
 *
 * @author channarith.bong
 */

public class OrderService {

    private static OrderService instance;
    private CartProductItem cartProductItem;
    private String token;
    private CartItemRequest cartItemRequestItem;
    private InvokeOnCompleteAsync onCompleteAsync;

    /**
     * @param token
     */
    private OrderService(String token) {
        cartProductItem = new CartProductItem();
        this.token = token;
    }

    /**
     * @param token
     * @return
     */
    public static OrderService getInstance(String token) {
        if (instance == null) {
            instance = new OrderService(token);
        }
        return instance;
    }

    /**
     * @param cartItemRequestItem
     * @param onCompleteAsync
     * @return
     */
    public CartProductItem addItemToCart(CartItemRequest cartItemRequestItem, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        this.cartItemRequestItem = cartItemRequestItem;
        generateQuoteId();
        return cartProductItem;
    }

    /**
     *
     */
    private synchronized void registerItem() {
        new AddItemToCart(cartItemRequestItem, token, new AddItemToCart.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(CartProductItem cart) {
                if (cart != null) {
                    if (onCompleteAsync != null) {
                        cartProductItem = cart;
                        onCompleteAsync.onComplete(cart);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (onCompleteAsync != null) {
                    onCompleteAsync.onError(e);
                }
            }
        });
    }

    /**
     *
     */
    private synchronized void generateQuoteId() {
        new GenerateQuote(token, new GenerateQuote.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(String quoteId) {
                if (quoteId != null) {
                    cartItemRequestItem.getCartProductItem().setQuoteId(quoteId);
                    registerItem();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (onCompleteAsync != null) {
                    onCompleteAsync.onError(e);
                }
            }
        });
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(CartProductItem cartProductItem);

        void onError(Throwable e);
    }
}
