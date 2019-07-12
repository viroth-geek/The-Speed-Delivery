package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.magento.catalog.Catalog;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.product.ProductService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 12/27/17.
 * @author channarith.bong
 */

public class FetchCategory implements Observer<Catalog> {

    private InvokeOnCompleteAsync completeAsync;
    private Catalog catalog;
    private Disposable disposable;

    /**
     *
     * @param completeAsync
     */
    public FetchCategory(InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request();
    }

    /**
     *
     */
    private void request() {
        ServiceGenerator
                .createService(ProductService.class)
                .getCatalogLevelOneRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        completeAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        completeAsync.onComplete(catalog);
    }

    /**
     *
     * @return
     */
    public Catalog getCatalog() {
        return catalog;
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(Catalog catalog);
        void onError(Throwable e);
    }
}
