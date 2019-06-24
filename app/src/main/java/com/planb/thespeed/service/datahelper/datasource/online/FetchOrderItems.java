package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.ProductItem;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.order.OrderService;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NokorGroup on 6/5/18.
 */

public class FetchOrderItems implements Observer<List<ProductItem>> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private List<ProductItem> productItems;
    private Disposable disposable;

    /**
     *
     * @param incrementId
     * @param onCompleteAsync
     */
    public FetchOrderItems(String incrementId, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(incrementId);
    }

    /**
     *
     * @param incrementId
     */
    private void request(String incrementId) {
        ServiceGenerator.createService(OrderService.class)
                .getOrderItems(incrementId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(List<ProductItem> productItems) {
        this.productItems = productItems;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(this.productItems);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<ProductItem> productItems);

        void onError(Throwable e);
    }
}
