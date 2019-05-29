package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.OrderStatus;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.order.OrderService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 8/21/18.
 */

public class FetchOrderStatusComment extends BaseService<List<OrderStatus>> {

    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchOrderStatusComment(Long orderId, String token, InvokeOnCompleteAsync<List<OrderStatus>> onCompleteAsync) {
        super(onCompleteAsync);
        request(orderId, token);
    }

    /**
     *
     * @param orderId Long
     */
    private void request(Long orderId, String token) {
        ServiceGenerator.createService(OrderService.class, token)
                .getOrderStatusComment(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
