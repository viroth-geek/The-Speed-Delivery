package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.PhoneNumber;
import com.iota.eshopping.model.phone.PhoneResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FetchCustomerByPhone implements Observer<PhoneResponse> {

    private ILoginOnCompleteAsync iLoginOnCompleteAsync;
    private PhoneResponse phoneResponse;
    private Disposable disposable;


    /**
     * @param phone
     * @param iLoginOnCompleteAsync
     */
    public FetchCustomerByPhone(PhoneNumber.CustomerPhone phone, ILoginOnCompleteAsync iLoginOnCompleteAsync) {
        this.iLoginOnCompleteAsync = iLoginOnCompleteAsync;
        requestCustomerByPhone(phone);
    }

    private void requestCustomerByPhone(PhoneNumber.CustomerPhone phone) {
//        ServiceGenerator
//                .createService(CustomerService.class)
//                .getCustomerByPhone(phone)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(this);

    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(PhoneResponse phoneResponse) {
        this.phoneResponse = phoneResponse;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        iLoginOnCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        iLoginOnCompleteAsync.onComplete(phoneResponse);
    }


    public interface ILoginOnCompleteAsync {
        void onComplete(PhoneResponse phoneResponse);

        void onError(Throwable e);
    }

}
