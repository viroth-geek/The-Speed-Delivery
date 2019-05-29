package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.magento.store.StoreFee;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.store.StoreService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NokorGroup on 6/8/18.
 */

public class CalculateServiceFee implements Observer<Float> {
    
    private InvokeOnCompleteAsync onCompleteAsync;
    private Float serviceFee;
    private Disposable disposable;

    /**
     *
     * @param storeFee
     * @param onCompleteAsync
     */
    public CalculateServiceFee(StoreFee storeFee, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(storeFee);
    }

    /**
     *
     * @param storeFee
     */
    private void request(StoreFee storeFee) {
        ServiceGenerator.createService(StoreService.class)
                .calculateServiceFee(storeFee)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(Float aFloat) {
        this.serviceFee = aFloat;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        this.onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        this.onCompleteAsync.onComplete(this.serviceFee);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(Float serviceFee);

        void onError(Throwable e);
    }
    
}
