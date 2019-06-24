package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.modelForView.Address;
import com.planb.thespeed.model.modelForView.CreateAddress;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.user.CustomerService;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NokorGroup on 6/11/18.
 */

public class UpdateAddress implements Observer<List<Address>> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private List<Address> addresses;
    private Disposable disposable;

    /**
     *
     * @param createAddress
     * @param onCompleteAsync
     */
    public UpdateAddress(CreateAddress createAddress, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(createAddress);
    }

    /**
     *
     * @param createAddress
     */
    private void request(CreateAddress createAddress) {
        ServiceGenerator.createService(CustomerService.class)
                .updateAddress(createAddress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(this.addresses);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<Address> addresses);
        void onError(Throwable e);
    }
}
