package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.Customer;
import com.iota.eshopping.model.Login;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.user.ClientTokenService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 1/10/18.
 */
public class GenerateToken implements Observer<String> {

    private InvokeOnCompleteAsync completeAsync;
    private Customer customerInfo;
    private String token;
    private Disposable disposable;

    /**
     * @param login
     * @param completeAsync
     */
    public GenerateToken(Login login, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(login);
    }

    /**
     * @param login
     */
    private void request(Login login) {
        ServiceGenerator
                .createService(ClientTokenService.class)
                .getAccessCustomerToken(login)
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
        token = s;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        completeAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        completeAsync.onComplete(token);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(String token);

        void onError(Throwable e);
    }
}
