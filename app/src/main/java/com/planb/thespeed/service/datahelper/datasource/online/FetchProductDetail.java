package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.Product;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.product.ProductService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 12/27/17.
 *
 * @author channarith.bong
 */

public class FetchProductDetail implements Observer<Product> {

    private InvokeOnCompleteAsync completeAsync;
    private Product productDetail;
    private Disposable disposable;

    /**
     * @param sku
     * @param completeAsync
     */
    public FetchProductDetail(String sku, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(sku);
    }

    /**
     * @param sku
     */
    public void request(String sku) {
        ServiceGenerator
                .createService(ProductService.class)
                .getProductDetailBySku(sku)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(Product productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        completeAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        completeAsync.onComplete(productDetail);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(Product productDetail);

        void onError(Throwable e);
    }
}
