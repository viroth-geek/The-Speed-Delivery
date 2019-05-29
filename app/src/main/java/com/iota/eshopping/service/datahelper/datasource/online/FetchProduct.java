package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.magento.catalog.ProductSku;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.product.ProductService;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 12/27/17.
 *
 * @author channarith.bong
 */

public class FetchProduct implements Observer<List<ProductSku>> {

    private InvokeOnCompleteAsync completeAsync;
    private List<ProductSku> productSkus;
    private Disposable disposable;

    /**
     * @param id
     * @param completeAsync
     */
    public FetchProduct(Long id, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(id);
    }

    /**
     * @param id
     */
    public void request(Long id) {
        ServiceGenerator
                .createService(ProductService.class)
                .getCatalogLevelTwoRx(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(List<ProductSku> productSkus) {
        this.productSkus = productSkus;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        completeAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        completeAsync.onComplete(productSkus);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<ProductSku> productSkus);

        void onError(Throwable e);
    }
}
