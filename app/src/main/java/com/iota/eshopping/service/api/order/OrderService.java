package com.iota.eshopping.service.api.order;

import com.iota.eshopping.model.DriverInfo;
import com.iota.eshopping.model.DriverLocation;
import com.iota.eshopping.model.OrderDetail;
import com.iota.eshopping.model.OrderStatus;
import com.iota.eshopping.model.ProductItem;
import com.iota.eshopping.model.magento.addToCart.CartItemRequest;
import com.iota.eshopping.model.magento.addToCart.CartProductItem;
import com.iota.eshopping.model.magento.order.CheckoutResponse;
import com.iota.eshopping.model.magento.order.ItemList;
import com.iota.eshopping.model.magento.order.OrderProduct;
import com.iota.eshopping.model.magento.order.ShipmentMethodInfo;
import com.iota.eshopping.model.magento.order.ShippingBilling;
import com.iota.eshopping.model.magento.order.ShippingMethod;
import com.iota.eshopping.model.order.OrderItem;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by channarith.bong on 1/9/18.
 *
 * @author channarith.bong
 */
public interface OrderService {

    /**
     * Get Quote ID
     *
     * @return String id of quote
     */
    @POST("V1/carts/mine")
    Observable<String> getQuoteID();

    /**
     * Add item to cart
     *
     * @param CartItemRequest
     * @return CartProductItem
     * @see CartProductItem
     */
    @POST("V1/carts/mine/items")
    Observable<CartProductItem> addItemToCart(@Body CartItemRequest CartItemRequest);

    /**
     * @param mail
     * @return
     */
    @POST("V2/eshopping/store/clearCart/{mail}")
    Observable<Integer> clearItemFromCart(@Path("mail") String mail);

    /**
     * @param id
     * @return OrderDetail
     * @see OrderDetail
     */
    @GET("V1/orders/{order_id}")
    Observable<OrderDetail> getOrderDetail(@Path("order_id") Long id);

    /**
     * Get order detail list
     *
     * @param stringHashMap
     * @return ItemList
     * @see ItemList
     */
    @GET("V1/orders/?")
    Observable<ItemList> getOrderDetailList(@QueryMap HashMap<String, String> stringHashMap);

    /**
     * @param shippingMethod
     * @return List of ShipmentMethodInfo
     * @see ShipmentMethodInfo
     */
    @POST("V1/carts/mine/estimate-shipping-methods")
    Observable<List<ShipmentMethodInfo>> estimateShippingMethods(@Body ShippingMethod shippingMethod);

    /**
     * @param shippingBilling
     * @return CheckoutResponse
     * @see CheckoutResponse
     */
    @POST("V1/carts/mine/shipping-information")
    Observable<CheckoutResponse> summitShipment(@Body ShippingBilling shippingBilling);

    /**
     * @param orderProduct
     * @return List of Bulk Order
     */
    @POST("V2/eshopping/store/orderProduct")
    Observable<List<String>> bulkOrder(@Body OrderProduct orderProduct);

    /**
     *
     * @param id
     * @return
     */
    @GET("V2/eshopping/order/getAddressLatitudeLongitude/{order_id}")
    Observable<List<String>> getOrderDetailLatLng(@Path("order_id") Long id);

    /**
     *
     * @param id
     * @return
     */
    @GET("V2/eshopping/order/getServiceFee/{order_id}")
    Observable<String> getOrderServiceFee(@Path("order_id") Long id);

    /**
     *
     * @param id
     * @return
     */
    @GET("V2/eshopping/order/getDeliveryDate/{order_id}")
    Observable<String> getDeliveryDate(@Path("order_id") Long id);

    /**
     *
     * @param id
     * @return
     */
    @GET("V2/eshopping/order/getDeliveryComment/{order_id}")
    Observable<String> getOrderComment(@Path("order_id") Long id);

    /**
     *
     * @param incrementId
     * @return
     */
    @GET("V2/eshopping/order/getItemsByIncrementId/{increment_id}")
    Observable<List<ProductItem>> getOrderItems(@Path("increment_id") String incrementId);

    /**
     * @see OrderItem
     * @param orderId orderId
     * @return List of OrderItem
     */
    @GET("V2/eshopping/order/visibaleItem/{orderId}")
    Observable<List<OrderItem>> getOrderItemsByOrderId(@Path("orderId") Long orderId);

    /**
     * get order status comment
     * @param orderId Long
     * @return List of OrderStatus
     */
    @GET("V2/eshopping/order/statusHistories/{orderId}")
    Observable<List<OrderStatus>> getOrderStatusComment(@Path("orderId") Long orderId);

    /**
     * Get driver location history
     * @param orderId Long
     * @return List<DriverLocation>
     */
    @GET("V2/eshopping/driver/getDriverLocationByOrderId/{orderId}")
    Observable<List<DriverLocation>> getLocationHistoryByOrderId(@Path("orderId") Long orderId);

    /**
     * Get last driver location history
     * @param orderId Long
     * @return List<DriverLocation>
     */
    @GET("V2/eshopping/driver/getDriverLastLocation/{orderId}")
    Observable<List<DriverLocation>> getLastDriverLocationByOrderId(@Path("orderId") Long orderId);

    /**
     *
     * @param orderId
     * @return
     */
    @GET("V2/eshopping/driver/getDriverInfoByOrderId/{orderId}")
    Observable<List<DriverInfo>> getDriverInfoByOrderId(@Path("orderId") Long orderId);
}
