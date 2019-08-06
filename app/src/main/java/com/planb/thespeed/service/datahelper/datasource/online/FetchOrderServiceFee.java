package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.order.OrderService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NokorGroup on 5/30/18.
 */

public class FetchOrderServiceFee implements Observer<String> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private String serviceFee;
    private Disposable disposable;

    /**
     *
     * @param id
     * @param onCompleteAsync
     */
    public FetchOrderServiceFee(Long id, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(id);
    }

    /**
     *
     * @param id
     */
    private void request(Long id) {
        ServiceGenerator.createService(OrderService.class)
                .getOrderServiceFee(id)
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
        this.serviceFee = s;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(serviceFee);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(String orderServiceFee);

        void onError(Throwable e);
    }
}
