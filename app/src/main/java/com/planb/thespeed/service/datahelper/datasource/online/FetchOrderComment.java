package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.order.OrderService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

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
