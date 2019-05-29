package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.page.WebPageService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author channarith.bong
 */

public class FetchWebPage implements Observer<String> {

    private InvokeOnCompleteAsync completeAsync;
    private String content;
    private Disposable disposable;

    /**
     * @param key
     * @param completeAsync
     */
    public FetchWebPage(String key, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(key);
    }

    /**
     * @param key
     */
    private void request(String key) {
        ServiceGenerator
                .createService(WebPageService.class)
                .getContent(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(String content) {
        this.content = content;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        completeAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        completeAsync.onComplete(content);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(String content);

        void onError(Throwable e);
    }
}
