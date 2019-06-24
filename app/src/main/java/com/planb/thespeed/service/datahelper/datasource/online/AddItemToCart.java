package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.magento.addToCart.CartItemRequest;
import com.planb.thespeed.model.magento.addToCart.CartProductItem;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.order.OrderService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 1/9/18.
 *
 * @author channarith.bong
 */
public class AddItemToCart implements Observer<CartProductItem> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private CartProductItem cartProductItem;
    private Disposable disposable;

    /**
     * @param CartItemRequest
     * @param token
     * @param onCompleteAsync
     */
    public AddItemToCart(CartItemRequest CartItemRequest, String token, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        if (token != null) {
            request(CartItemRequest, token);
        } else {
            this.onCompleteAsync.onError(new Throwable("No Token"));
        }
    }

    /**
     * @param CartItemRequest
     * @param token
     */
    private void request(CartItemRequest CartItemRequest, String token) {
        ServiceGenerator
                .createService(OrderService.class, token)
                .addItemToCart(CartItemRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(CartProductItem cartProductItem) {
        this.cartProductItem = cartProductItem;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(cartProductItem);
    }

    public interface InvokeOnCompleteAsync {
        void onComplete(CartProductItem cartProductItem);

        void onError(Throwable e);
    }
}