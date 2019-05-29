package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.order.OrderService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 6/4/18.
 */

public class FetchOrderComment extends BaseService<String> {

    /**
     * Constructor
     * @param orderId Long
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchOrderComment(Long orderId, InvokeOnCompleteAsync<String> onCompleteAsync) {
        super(onCompleteAsync);
        request(orderId);
    }

    /**
     *
     * @param orderId
     */
    private void request(Long orderId) {
        ServiceGenerator.createService(OrderService.class)
                .getOrderComment(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
