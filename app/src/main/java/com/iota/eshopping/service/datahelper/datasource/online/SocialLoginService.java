package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.form.SocialLoginForm;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.user.ClientTokenService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 8/10/18.
 */

public class SocialLoginService implements Observer<String> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private String token;
    private Disposable disposable;

    /**
     * constructor
     * @param socialLoginForm SocialLoginForm
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public SocialLoginService(SocialLoginForm socialLoginForm, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(socialLoginForm);
    }

    /**
     * request service
     * @param socialLoginForm SocialLoginForm
     */
    private void request(SocialLoginForm socialLoginForm) {
        ServiceGenerator.createService(ClientTokenService.class)
                .getAccessCustomerTokenWithSocialAccount(socialLoginForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(String s) {
        this.token = s;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(this.token);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(String token);

        void onError(Throwable e);
    }
}
