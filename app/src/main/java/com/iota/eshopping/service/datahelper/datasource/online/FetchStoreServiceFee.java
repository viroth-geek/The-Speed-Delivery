package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.store.StoreService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NokorGroup on 5/31/18.
 */

public class FetchStoreServiceFee implements Observer<String> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private String serviceFee;
    private Disposable disposable;

    /**
     *
     * @param storeId
     * @param onCompleteAsync
     */
    public FetchStoreServiceFee(Long storeId, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(storeId);
    }

    /**
     *
     * @param storeId
     */
    private void request(Long storeId) {
        ServiceGenerator.createService(StoreService.class)
                .getStoreServiceFee(storeId)
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
        void onComplete(String serviceFee);

        void onError(Throwable e);
    }
}
