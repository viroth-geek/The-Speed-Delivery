package com.iota.eshopping.service.api.store;

import com.google.android.gms.maps.model.LatLng;
import com.iota.eshopping.model.ExchangeRate;
import com.iota.eshopping.model.ServerDateTime;
import com.iota.eshopping.model.StoreDeliveryFee;
import com.iota.eshopping.model.Tag;
import com.iota.eshopping.model.form.FormForGetDeliveryFee;
import com.iota.eshopping.model.form.FormForGetDirection;
import com.iota.eshopping.model.magento.store.StoreFee;
import com.iota.eshopping.model.magento.store.storeList.ListStore;
import com.iota.eshopping.model.magento.store.storeList.StoreRestriction;
import com.iota.eshopping.model.magento.store.storeList.StoreView;
import com.iota.eshopping.model.direction.Direction;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by channarith.bong on 12/21/17.
 *
 * @author channarith.bong
 */
public interface StoreService {

    /**
     * Get all store view
     *
     * @return List of StoreView
     * @see StoreView
     */
    @GET("V1/store/storeViews")
    Observable<List<StoreView>> getStoreViews();

    /**
     * Get store view by id
     *
     * @param id
     * @return List of store view
     * @see StoreView
     */
    @GET("V2/eshopping/store/view/{id}")
    Observable<List<StoreView>> getStoreViewsById(@Path("id") Long id);

    /**
     * getStoreViewByIdWithSizeProduct
     * @param storeId Long
     * @param productSize Long
     * @return Observable<List<StoreView>>
     */
    @GET("V2/eshopping/store/getStoreById/{storeId}/productList/{productSize}")
    Observable<List<StoreView>> getStoreViewByIdWithSizeProduct(@Path("storeId") Long storeId, @Path("productSize") int productSize);

    /**
     * Get store view as paging
     *
     * @param page
     * @param limit
     * @return List of ListStore
     * @see ListStore
     */
    @GET("V2/eshopping/store/{page}/{limit}")
    Observable<List<ListStore>> getStoreViewsByPaging(
            @Path("page") Integer page,
            @Path("limit") Integer limit);

    /**
     * Get store store view paging with provided coordinate
     *
     * @param page
     * @param limit
     * @param deliverLatLng
     * @return List of list store
     * @see LatLng
     * @see ListStore
     */
    @GET("V2/eshopping/store/{page}/{limit}/{deliverLatLng}")
    Observable<List<ListStore>> getStoreViewsByPaging(
            @Path("page") Integer page,
            @Path("limit") Integer limit,
            @Path("deliverLatLng") LatLng deliverLatLng);

    /**
     * Get list store by search restriction
     *
     * @param restriction
     * @return List of list store
     * @see StoreRestriction
     * @see ListStore
     */
    @POST("V2/eshopping/storeList")
    Observable<List<ListStore>> getStoreList(@Body StoreRestriction restriction);

    /**
     *
     * @param storeId
     * @return
     */
    @GET("V2/eshopping/order/getServiceFeeByStore/{storeId}")
    Observable<String> getStoreServiceFee(@Path("storeId") Long storeId);

    /**
     *
     * @param storeFee
     * @return
     */
    @POST("V2/eshopping/order/calculateFee")
    Observable<Float> calculateServiceFee(@Body StoreFee storeFee);

    /**
     *
     * @return
     */
    @GET("V2/eshopping/sale/currency/getDollarRielRate")
    Observable<List<ExchangeRate>> getExchangeRate();

    /**
     *
     * @return list of ServerDateTime
     */
    @GET("V2/eshopping/store/availableOrderTime")
    Observable<List<ServerDateTime>> getServerDateTime();

    /**
     * Get nearest store
     * @param storeRestriction StoreRestriction
     * @return Observable
     */
    @POST("V2/eshopping/store/getNearestStoreIds")
    Observable<List<Long>> getNearestStores(@Body StoreRestriction storeRestriction);

    /**
     * Get delivery fee
     * @param formForGetDeliveryFee FormForGetDeliveryFee
     * @return List<StoreDeliveryFee>
     */
    @POST("V2/eshopping/store/getShippingMethodByStoreId")
    Observable<List<StoreDeliveryFee>> getDeliveryFee(@Body FormForGetDeliveryFee formForGetDeliveryFee);

    /**
     * Get direction
     * @param formForGetDirection FormForGetDirection
     * @return FormForGetDirection
     */
    @POST("V2/eshopping/map/getMapDirection")
    Observable<List<Direction>> getDirection(@Body FormForGetDirection formForGetDirection);

    /**
     *
     * @return
     */
    @GET("V2/eshopping/store/getTags")
    Observable<List<Tag>> getTags();
}
