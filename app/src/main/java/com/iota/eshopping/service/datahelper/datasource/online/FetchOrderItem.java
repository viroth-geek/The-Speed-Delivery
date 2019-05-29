package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.order.OrderItem;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.order.OrderService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 7/23/18.
 */

public class FetchOrderItem extends BaseService<List<OrderItem>> {

    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchOrderItem(Long orderId, InvokeOnCompleteAsync<List<OrderItem>> onCompleteAsync) {
        super(onCompleteAsync);
        request(orderId);
    }

    /**
     *
     * @param orderId Long
     */
    private void request(Long orderId) {
        ServiceGenerator.createService(OrderService.class)
                .getOrderItemsByOrderId(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
