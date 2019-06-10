package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.Customer;
import com.iota.eshopping.security.KeyManagement;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.user.CustomerService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 6/8/18.
 */

public class FetchAddressList extends BaseService<Customer> {

    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchAddressList(Long userId, InvokeOnCompleteAsync<Customer> onCompleteAsync) {
        super(onCompleteAsync);
        request(userId);
    }

    /**
     *
     * @param userId Long
     */
    private void request(Long userId) {
        ServiceGenerator.createService(CustomerService.class, KeyManagement.getInstance().getTokenAdmin())
                .getAddressList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
