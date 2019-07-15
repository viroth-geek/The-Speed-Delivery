package com.planb.thespeed.service;

import com.planb.thespeed.model.Customer;
import com.planb.thespeed.model.UserSecure;
import com.planb.thespeed.service.datahelper.datasource.online.FetchCustomer;
import com.planb.thespeed.service.datahelper.mapper.DataMatcher;


/**
 * Created by channarith.bong on 1/9/18.
 *
 * @author channarith.bong
 */

public class CustomerService {

    private static CustomerService instance;
    private InvokeOnCompleteAsync onCompleteAsync;
    private Customer customerInfo;

    /**
     *
     */
    private void CustomerService() {
        customerInfo = new Customer();
    }

    /**
     * @return
     */
    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    /**
     * @param customer
     * @param onCompleteAsync
     * @return
     */
    public Customer createCustomer(UserSecure customer, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        UserSecure createCustomer = DataMatcher.getInstance().getUserSecure(customer);
        fetchToCreateCustomer(createCustomer);
        return customerInfo;
    }

    /**
     * @param customer
     */
    private void fetchToCreateCustomer(UserSecure customer) {
        new FetchCustomer(customer, new FetchCustomer.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(Customer customer) {
                if (onCompleteAsync != null) {
                    customerInfo = customer;
                    onCompleteAsync.onComplete(customer);
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

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(Customer customerInfo);

        void onError(Throwable e);
    }

}
