package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.magento.order.ShipmentMethodInfo;
import com.planb.thespeed.model.magento.order.ShippingMethod;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.order.OrderService;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 2/2/18.
 *
 * @author channarith.bong
 */

public class GenerateShipment implements Observer<List<ShipmentMethodInfo>> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private List<ShipmentMethodInfo> shipmentMethods;
    private Disposable disposable;

    /**
     * @param method
     * @param token
     * @param onCompleteAsync
     */
    public GenerateShipment(ShippingMethod method, String token, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(method, token);
    }

    /**
     * @param method
     * @param token
     */
    private void request(ShippingMethod method, String token) {
        ServiceGenerator
                .createService(OrderService.class, token)
                .estimateShippingMethods(method)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(List<ShipmentMethodInfo> shipmentMethods) {
        this.shipmentMethods = shipmentMethods;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(shipmentMethods);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<ShipmentMethodInfo> shipmentMethods);

        void onError(Throwable e);
    }
}
