package com.iota.eshopping.service;

import com.iota.eshopping.model.OrderDetail;
import com.iota.eshopping.service.datahelper.datasource.online.FetchOrderDetail;

/**
 * Created by channarith.bong on 1/22/18.
 *
 * @author channarith.bong
 */

public class OrderDetailService {

    private static OrderDetailService instance;
    private InvokeOnCompleteAsync onCompleteAsync;
    private OrderDetail orderDetail;

    /**
     *
     */
    private OrderDetailService() {
        orderDetail = new OrderDetail();
    }

    /**
     * @return
     */
    public static OrderDetailService getInstance() {
        if (instance == null) {
            instance = new OrderDetailService();
        }
        return instance;
    }

    /**
     * @param id
     * @param onCompleteAsync
     * @return
     */
    public OrderDetail getOrderDetail(Long id, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        fetchOrderDetail(id);
        return orderDetail;
    }

    /**
     * @param id
     */
    private void fetchOrderDetail(Long id) {
        new FetchOrderDetail(id, new FetchOrderDetail.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(OrderDetail detail) {
                if (onCompleteAsync != null) {
                    onCompleteAsync.onComplete(detail);
                    orderDetail = detail;
                }
            }

            @Override
            public void onError(Throwable e) {
                if (onCompleteAsync != null) {
                    onCompleteAsync.onError(e);
                }
            }
        });
    }

    public void getOrderLatLng(Long id, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;

    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(OrderDetail orderDetail);

        void onError(Throwable e);
    }
}
