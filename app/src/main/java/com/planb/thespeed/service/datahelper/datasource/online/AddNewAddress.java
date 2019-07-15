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
 * Created by NokorGroup on 6/8/18.
 */

public class AddNewAddress implements Observer<List<Address>> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private List<Address> addresses;
    private Disposable disposable;

    /**
     *
     * @param address
     * @param onCompleteAsync
     */
    public AddNewAddress(CreateAddress address, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(address);
    }

    /**
     *
     * @param address
     */
    private void request(CreateAddress address) {
        ServiceGenerator.createService(CustomerService.class)
                .addNewAddress(address)
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
