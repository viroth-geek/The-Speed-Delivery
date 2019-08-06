package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.form.ForgetPasswordForm;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.user.CustomerService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 8/13/18.
 */

public class ForgetPasswordService implements Observer<Boolean> {

    private Boolean status;
    private InvokeOnCompleteAsync onCompleteAsync;
    private Disposable disposable;

    /**
     * Constructor
     * @param email String
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public ForgetPasswordService(String email, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(email);
    }

    /**
     * request method
     * @param email String
     */
    private void request(String email) {
        ServiceGenerator.createService(CustomerService.class)
                .sendForgetPassword(prepareFormForgetPassword(email))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    /**
     * form forget password
     * @see ForgetPasswordForm
     * @param email String
     * @return ForgetPasswordForm
     */
    private ForgetPasswordForm prepareFormForgetPassword(String email) {
        return new ForgetPasswordForm(email, "email_reset", 1L);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(Boolean aBoolean) {
        this.status = aBoolean;
    }

    @Override
    public void onError(Throwable e) {
        this.disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        this.disposable.dispose();
        onCompleteAsync.onComplete(this.status);
    }



    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(Boolean status);

        void onError(Throwable e);
    }
}
