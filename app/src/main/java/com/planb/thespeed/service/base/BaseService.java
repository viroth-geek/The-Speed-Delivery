package com.planb.thespeed.service.base;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author yeakleang.ay on 9/5/18.
 */

public abstract class BaseService<T> implements Observer<T> {

    private T data;
    private InvokeOnCompleteAsync<T> onCompleteAsync;
    private Disposable disposable;

    /**
     * Constructor
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public BaseService(InvokeOnCompleteAsync<T> onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(T data) {
        this.data = data;
    }

    @Override
    public void onError(Throwable e) {
        this.disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        this.disposable.dispose();
        onCompleteAsync.onComplete(this.data);
    }

}
