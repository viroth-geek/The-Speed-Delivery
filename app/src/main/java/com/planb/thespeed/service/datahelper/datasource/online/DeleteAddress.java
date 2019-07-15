package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.user.CustomerService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NokorGroup on 6/11/18.
 */

public class DeleteAddress implements Observer<Integer> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private Integer result;
    private Disposable disposable;

    /**
     *
     * @param addressId
     * @param onCompleteAsync
     */
    public DeleteAddress(Long addressId, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(addressId);
    }

    /**
     *
     * @param addressId
     */
    private void request(Long addressId) {
        ServiceGenerator.createService(CustomerService.class)
                .deleteAddress(addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(Integer integer) {
        this.result = integer;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(this.result);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(Integer result);
        void onError(Throwable e);
    }
}
