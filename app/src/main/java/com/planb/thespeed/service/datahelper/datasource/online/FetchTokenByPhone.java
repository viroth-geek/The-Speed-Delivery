package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.PhoneNumber;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.user.CustomerService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FetchTokenByPhone implements Observer<String> {

    private ILoginOnCompleteAsync iLoginOnCompleteAsync;
    private String token;
    private Disposable disposable;


    /**
     * @param phone
     * @param iLoginOnCompleteAsync
     */
    public FetchTokenByPhone(PhoneNumber.CustomerPhone phone, ILoginOnCompleteAsync iLoginOnCompleteAsync) {
        this.iLoginOnCompleteAsync = iLoginOnCompleteAsync;
        requestCustomerByPhone(phone);
    }

    private void requestCustomerByPhone(PhoneNumber.CustomerPhone phone) {
        ServiceGenerator
                .createService(CustomerService.class)
                .getCustomerByPhone(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(String token) {
        this.token = token;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        iLoginOnCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        iLoginOnCompleteAsync.onComplete(token);
    }


    public interface ILoginOnCompleteAsync {
        void onComplete(String token);

        void onError(Throwable e);
    }

}