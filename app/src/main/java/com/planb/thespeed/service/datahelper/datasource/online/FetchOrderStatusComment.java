package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.OrderStatus;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.order.OrderService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

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
