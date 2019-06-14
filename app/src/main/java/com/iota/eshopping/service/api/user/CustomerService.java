package com.iota.eshopping.service.api.user;

import com.iota.eshopping.model.Customer;
import com.iota.eshopping.model.CustomerPhoneNumber;
import com.iota.eshopping.model.PhoneNumber;
import com.iota.eshopping.model.TokenPhoneNumber;
import com.iota.eshopping.model.UserSecure;
import com.iota.eshopping.model.form.ForgetPasswordForm;
import com.iota.eshopping.model.modelForView.Address;
import com.iota.eshopping.model.modelForView.CreateAddress;
import com.iota.eshopping.model.phone.PhoneResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by channarith.bong on 1/9/18.
 *
 * @author channarith.bong
 */
public interface CustomerService {

    /**
     * Create new customer
     *
     * @param customer
     * @return New customer
     * @see Customer
     * @see UserSecure
     */
    @POST("V1/customers")
    Observable<Customer> createCustomer(@Body UserSecure customer);

    /**
     * Get current customer info
     *
     * @return Customer
     * @see Customer
     */
    @GET("V1/customers/me")
    Observable<Customer> getCustomerInfo();

    /**
     * Update customer by id
     *
     * @param id       ID of customer
     * @param customer
     * @return Customer
     * @see Customer
     * @see UserSecure
     */
    @PUT("V1/customers/{id}")
    Observable<Customer> updateCustomer(@Path("id") Long id, @Body UserSecure customer);

    /**
     *
     * @param userId
     * @return
     */
    @GET("V1/customers/{userId}")
    Observable<Customer> getAddressList(@Path("userId") Long userId);

    /**
     *
     * @param address
     * @return
     */
    @POST("V2/eshopping/customer/addNewAddress")
    Observable<List<Address>> addNewAddress(@Body CreateAddress address);

    /**
     *
     * @param addressId
     * @return
     */
    @DELETE("V2/eshopping/customer/deleteAddressById/{addressId}")
    Observable<Integer> deleteAddress(@Path("addressId") Long addressId);

    /**
     *
     * @param createAddress
     * @return
     */
    @POST("V2/eshopping/customer/updateAddress")
    Observable<List<Address>> updateAddress(@Body CreateAddress createAddress);

    @PUT("V1/customers/password")
    Observable<Boolean> sendForgetPassword(@Body ForgetPasswordForm forgetPasswordForm);


    @POST("V2/eshopping/customer/registerPhoneNumber")
    Observable<String> getCustomerByPhone(@Body PhoneNumber.CustomerPhone phoneNumber);

    @POST("V2/eshopping/customer/loginByPhoneNumber")
    Observable<CustomerPhoneNumber> loginByPhoneNumber(@Body TokenPhoneNumber tokenPhoneNumber);

}
