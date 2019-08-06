package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.magento.order.CheckoutResponse;
import com.planb.thespeed.model.magento.order.ShippingBilling;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.order.OrderService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 2/9/18.
 *
 * @author channarith.bong
 */

public class SubmitShipment implements Observer<CheckoutResponse> {

    private InvokeOnCompleteAsync completeAsync;
    private CheckoutResponse checkoutResponse;
    private String token;
    private Disposable disposable;

    /**
     * @param billing
     * @param token
     * @param completeAsync
     */
    public SubmitShipment(ShippingBilling billing, String token, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        this.token = token;
        request(billing);
    }

    /**
     * @param billing
     */
    private void request(ShippingBilling billing) {
        ServiceGenerator
                .createService(OrderService.class, token)
                .summitShipment(billing)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(CheckoutResponse checkoutResponse) {
        this.checkoutResponse = checkoutResponse;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        completeAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        completeAsync.onComplete(checkoutResponse);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(CheckoutResponse checkoutResponse);

        void onError(Throwable e);
    }
}
