package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.modelForView.AddressByStreetString;
import com.planb.thespeed.model.modelForView.CreateAddressByStreetString;
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

public class AddNewAddressByStreetString implements Observer<List<AddressByStreetString>> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private List<AddressByStreetString> addresses;
    private Disposable disposable;

    /**
     * @param addressByStreetString
     * @param onCompleteAsync
     */
    public AddNewAddressByStreetString(CreateAddressByStreetString addressByStreetString, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(addressByStreetString);
    }

    /**
     * @param address
     */
    private void request(CreateAddressByStreetString address) {
        ServiceGenerator.createService(CustomerService.class)
                .addNewAddressByStreetString(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(List<AddressByStreetString> addresses) {
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
        void onComplete(List<AddressByStreetString> addresses);

        void onError(Throwable e);
    }
}
