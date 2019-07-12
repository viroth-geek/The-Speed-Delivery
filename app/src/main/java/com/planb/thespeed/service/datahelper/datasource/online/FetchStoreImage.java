package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.ImageResponse;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.image.ImageService;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author channarith.bong
 */
public class FetchStoreImage implements Observer<List<ImageResponse>> {

    private InvokeOnCompleteAsync completeAsync;
    private List<ImageResponse> imageResponseList;
    private Disposable disposable;

    /**
     * @param storeId
     * @param completeAsync
     */
    public FetchStoreImage(Long storeId, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(storeId);
    }

    /**
     * @param storeId
     */
    private void request(Long storeId) {
        ServiceGenerator
                .createService(ImageService.class)
                .getStoreImage(storeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(List<ImageResponse> imageResponseList) {
        this.imageResponseList = imageResponseList;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        completeAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        completeAsync.onComplete(imageResponseList);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<ImageResponse> imageResponseList);

        void onError(Throwable e);
    }
}
