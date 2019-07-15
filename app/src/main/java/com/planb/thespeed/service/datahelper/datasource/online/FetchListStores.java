package com.planb.thespeed.service.datahelper.datasource.online;

import com.google.android.gms.maps.model.LatLng;
import com.planb.thespeed.model.magento.store.storeList.ListStore;
import com.planb.thespeed.model.magento.store.storeList.StoreRestriction;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.store.StoreService;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 12/27/17.
 *
 * @author channarith.bong
 */
public class FetchListStores implements Observer<List<ListStore>> {

    private InvokeOnCompleteAsync completeAsync;
    private List<ListStore> listStores;
    private Disposable disposable;

    /**
     * @param completeAsync
     */
    public FetchListStores(int page, int limit, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(page, limit);
    }

    /**
     * @param deliverLatLng
     * @param completeAsync
     */
    public FetchListStores(LatLng deliverLatLng, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(deliverLatLng, 0, 100);
    }

    /**
     * @param page
     * @param limit
     * @param completeAsync
     */
    public FetchListStores(Integer page, Integer limit, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(page, limit);
    }

    /**
     * @param restriction
     * @param completeAsync
     */
    public FetchListStores(StoreRestriction restriction, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        request(restriction);
    }


    /**
     * @param page
     * @param limit
     */
    private void request(LatLng deliverLatLng, Integer page, Integer limit) {
        ServiceGenerator
                .createService(StoreService.class)
                .getStoreViewsByPaging(page, limit, deliverLatLng)
                .retry(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    /**
     * @param page
     * @param limit
     */
    private void request(Integer page, Integer limit) {
        ServiceGenerator
                .createService(StoreService.class)
                .getStoreViewsByPaging(page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    /**
     * @param restriction
     */
    private void request(StoreRestriction restriction) {
        ServiceGenerator
                .createService(StoreService.class)
                .getStoreList(restriction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(List<ListStore> storeViews) {
        this.listStores = storeViews;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        completeAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        completeAsync.onComplete(listStores);
    }

    /**
     * @return
     */
    public List<ListStore> getListStores() {
        return listStores;
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<ListStore> listStores);

        void onError(Throwable e);
    }
}
