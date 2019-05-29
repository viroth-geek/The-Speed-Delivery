package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.UserPlayerId;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.notification.NotificationService;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 8/22/18.
 */

public class SaveUserPlayerId implements Observer<List<UserPlayerId>> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private List<UserPlayerId> userPlayerIds;
    private Disposable disposable;

    /**
     * Constructor
     * @param userPlayerId UserPlayerId
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public SaveUserPlayerId(UserPlayerId userPlayerId, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(userPlayerId);
    }

    /**
     * request
     * @param userPlayerId UserPlayerId
     */
    private void request(UserPlayerId userPlayerId) {
        ServiceGenerator.createService(NotificationService.class)
                .saveUserPlayerId(userPlayerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(List<UserPlayerId> userPlayerIds) {
        this.userPlayerIds = userPlayerIds;
    }

    @Override
    public void onError(Throwable e) {
        this.disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        this.disposable.dispose();
        onCompleteAsync.onComplete(this.userPlayerIds);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<UserPlayerId> userPlayerIds);

        void onError(Throwable e);
    }
}
