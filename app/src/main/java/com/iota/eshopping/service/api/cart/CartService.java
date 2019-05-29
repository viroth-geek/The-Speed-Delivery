package com.iota.eshopping.service.api.cart;

import com.iota.eshopping.model.magento.addToCart.CartItemsRequest;
import com.iota.eshopping.model.magento.addToCart.ResponseAddToCart;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by channarith.bong on 2/19/18.
 *
 * @author channarith.bong
 */
public interface CartService {

    /**
     * Create new cart item
     *
     * @param addToCart CartItemsRequest
     * @return List of CartProductItem
     */
    @POST("V2/eshopping/store/addMultiItemToCartV2")
    Observable<List<ResponseAddToCart>> addNewCartItems(@Body CartItemsRequest addToCart);

}
