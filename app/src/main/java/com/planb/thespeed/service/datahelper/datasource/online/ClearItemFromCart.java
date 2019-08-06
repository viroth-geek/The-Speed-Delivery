package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.order.OrderService;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author channarith.bong
 */
public class ClearItemFromCart implements Observer<Integer> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private Integer itemNumber;
    private Disposable disposable;

    /**
     * @param mail
     * @param token
     * @param onCompleteAsync
     */
    public ClearItemFromCart(String mail, String token, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        if (token != null) {
            request(mail, token);
        } else {
            this.onCompleteAsync.onError(new Throwable("No Token"));
        }
    }

    /**
     * @param mail
     * @param token
     */
    private void request(String mail, String token) {
        ServiceGenerator
                .createService(OrderService.class, token)
                .clearItemFromCart(mail)
                .retry(3, throwable -> throwable instanceof SocketTimeoutException)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(itemNumber);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(Integer itemNumber);

        void onError(Throwable e);
    }
}