package com.iota.eshopping.service.api.image;

import com.iota.eshopping.model.ImageResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author channarith.bong
 */
public interface ImageService {

    /**
     * Get images of store
     *
     * @param storeId
     * @return List of ImageResponse
     * @see ImageResponse
     */
    @GET("V2/eshopping/image/storeImage/{storeId}")
    Observable<List<ImageResponse>> getStoreImage(@Path("storeId") Long storeId);

    /**
     * Get images of product
     *
     * @param productId
     * @return List of ImageResponse
     * @see ImageResponse
     */
    @GET("V2/eshopping/image/productImage/{productId}")
    Observable<List<ImageResponse>> getProductImage(@Path("productId") Long productId);
}
