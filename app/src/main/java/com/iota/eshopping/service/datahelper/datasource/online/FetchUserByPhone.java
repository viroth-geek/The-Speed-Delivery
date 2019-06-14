package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.CustomerPhoneNumber;
import com.iota.eshopping.model.TokenPhoneNumber;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.user.CustomerService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FetchUserByPhone implements Observer<CustomerPhoneNumber> {

    private ILoginOnCompleteAsync iLoginOnCompleteAsync;
    private CustomerPhoneNumber customer;
    private Disposable disposable;


    /**
     * @param tokenPhoneNumber
     * @param iLoginOnCompleteAsync
     */
    public FetchUserByPhone(TokenPhoneNumber tokenPhoneNumber, ILoginOnCompleteAsync iLoginOnCompleteAsync) {
        this.iLoginOnCompleteAsync = iLoginOnCompleteAsync;
        requestCustomerByPhone(tokenPhoneNumber);
    }

    private void requestCustomerByPhone(TokenPhoneNumber tokenPhoneNumber) {
        ServiceGenerator
                .createService(CustomerService.class)
                .loginByPhoneNumber(tokenPhoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);

    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(CustomerPhoneNumber customer) {
        this.customer = customer;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        iLoginOnCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        iLoginOnCompleteAsync.onComplete(customer);
    }

    public interface ILoginOnCompleteAsync {
        void onComplete(CustomerPhoneNumber customer);

        void onError(Throwable e);

    }

}
