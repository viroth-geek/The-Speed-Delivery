package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.order.OrderService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NokorGroup on 5/31/18.
 */

public class FetchDeliveryDate implements Observer<String> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private String orderDate;
    private Disposable disposable;

    /**
     *
     * @param orderId
     * @param onCompleteAsync
     */
    public FetchDeliveryDate(long orderId, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(orderId);
    }

    /**
     *
     * @param orderId
     */
    private void request(long orderId) {
        ServiceGenerator.createService(OrderService.class)
                .getDeliveryDate(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);

    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(String s) {
        this.orderDate = s;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(this.orderDate);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(String orderDate);

        void onError(Throwable e);
    }
}
