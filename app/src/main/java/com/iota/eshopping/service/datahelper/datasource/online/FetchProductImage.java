package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.ImageResponse;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.image.ImageService;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author channarith.bong
 */
public class FetchProductImage implements Observer<List<ImageResponse>> {

    private InvokeOnCompleteAsync completeAsync;
    private List<ImageResponse> imageResponseList;
    private Disposable disposable;

    /**
     * @param productId
     * @param completeAsync
     */
    public FetchProductImage(Long productId, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(productId);
    }

    /**
     * @param productId
     */
    private void request(Long productId) {
        ServiceGenerator
                .createService(ImageService.class)
                .getProductImage(productId)
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
