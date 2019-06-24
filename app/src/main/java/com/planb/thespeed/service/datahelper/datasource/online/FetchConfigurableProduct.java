package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.Product;
import com.planb.thespeed.model.ProductOptionBody;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.product.ProductService;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NokorGroup on 6/13/18.
 */

public class FetchConfigurableProduct implements Observer<List<Product>> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private List<Product> products;
    private Disposable disposable;

    /**
     *
     * @param productOptionBody
     * @param onCompleteAsync
     */
    public FetchConfigurableProduct(ProductOptionBody productOptionBody, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(productOptionBody);
    }

    /**
     *
     * @param productOptionBody
     */
    private void request(ProductOptionBody productOptionBody) {
        ServiceGenerator.createService(ProductService.class)
                .getChildProduct(productOptionBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);

    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(List<Product> products) {
        this.products = products;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(this.products);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<Product> products);
        void onError(Throwable e);
    }
}
