package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.OrderDetail;
import com.iota.eshopping.security.KeyManagement;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.order.OrderService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 1/22/18.
 *
 * @author channarith.bong
 */

public class FetchOrderDetail implements Observer<OrderDetail> {

    private InvokeOnCompleteAsync completeAsync;
    private OrderDetail orderDetail;
    private Disposable disposable;

    /**
     * @param orderId
     * @param completeAsync
     */
    public FetchOrderDetail(Long orderId, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(orderId);
    }

    /**
     * @param id
     */
    private void request(Long id) {
        ServiceGenerator
                .createService(OrderService.class, KeyManagement.getInstance().getTokenAdmin().trim())
                .getOrderDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void onNext(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public void onError(Throwable throwable) {
        disposable.dispose();
        completeAsync.onError(throwable);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        completeAsync.onComplete(orderDetail);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(OrderDetail orderDetail);

        void onError(Throwable e);
    }
}
