package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.Customer;
import com.iota.eshopping.model.UserSecure;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.user.CustomerService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 1/9/18.
 * @author channarith.bong
 */

public class FetchCustomer implements Observer<Customer> {

    private InvokeOnCompleteAsync completeAsync;
    private Customer customer;
    private Disposable disposable;

    /**
     *
     * @param token
     * @param completeAsync
     */
    public FetchCustomer(String token, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(token);
    }

    /**
     *
     * @param customer
     * @param completeAsync
     */
    public FetchCustomer(UserSecure customer, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(customer);
    }

    /**
     *
     * @param customer
     * @param token
     * @param completeAsync
     */
    public FetchCustomer(UserSecure customer, String token, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        requestUpdate(customer, token);
    }



    /**
     *
     * @param customer
     */
    private void request(UserSecure customer) {
        ServiceGenerator
                .createService(CustomerService.class)
                .createCustomer(customer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    /**
     *
     * @param customer
     */
    private void requestUpdate(UserSecure customer, String token) {
        ServiceGenerator
                .createService(CustomerService.class, token)
                .updateCustomer(customer.getCustomer().getId(), customer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    /**
     *
     * @param token
     */
    private void request(String token) {
        ServiceGenerator
                .createService(CustomerService.class, token)
                .getCustomerInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(Customer customerInfo) {
        this.customer = customerInfo;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        completeAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        completeAsync.onComplete(customer);
    }

    /**
     *
     * @return
     */
    public Customer getCustomerInfo() {
        return customer;
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(Customer customerInfo);
        void onError(Throwable e);
    }


    /**
     *
     * @return
     */


}
