package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.order.OrderService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 2/2/18.
 *
 * @author channarith.bong
 */

public class GenerateQuote implements Observer<String> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private String quoteId;
    private Disposable disposable;

    /**
     * @param token
     * @param onCompleteAsync
     */
    public GenerateQuote(String token, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(token);
    }

    /**
     * @param token
     */
    private void request(String token) {
        ServiceGenerator
                .createService(OrderService.class, token)
                .getQuoteID()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(String quoteId) {
        this.quoteId = quoteId;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(quoteId);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(String quoteId);

        void onError(Throwable e);
    }
}
