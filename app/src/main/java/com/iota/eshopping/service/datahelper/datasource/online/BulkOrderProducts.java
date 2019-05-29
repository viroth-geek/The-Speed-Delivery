package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.magento.order.OrderProduct;
import com.iota.eshopping.security.KeyManagement;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.order.OrderService;
import com.iota.eshopping.util.LoggerHelper;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 2/19/18.
 *
 * @author channarith.bong
 */

public class BulkOrderProducts implements Observer<List<String>> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private List<String> orderIncrements;
    private Disposable disposable;

    /**
     * @param orderProduct
     * @param onCompleteAsync
     */
    public BulkOrderProducts(OrderProduct orderProduct, String token, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        if (token != null) {
            request(orderProduct, token);
        } else {
            this.onCompleteAsync.onError(new Throwable("No Token"));
        }
    }

    /**
     * @param orderProduct
     */
    private void request(OrderProduct orderProduct, String token) {
        LoggerHelper.showDebugLog("Token: " + token);
        ServiceGenerator
                .createService(OrderService.class, token)
                .bulkOrder(orderProduct)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(List<String> orderIncrements) {
        this.orderIncrements = orderIncrements;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(orderIncrements);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<String> orderIncrements);

        void onError(Throwable e);
    }
}
