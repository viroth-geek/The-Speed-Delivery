package com.iota.eshopping.service.api.user;

import com.iota.eshopping.model.Login;
import com.iota.eshopping.model.form.SocialLoginForm;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by channarith.bong on 1/10/18.
 */
public interface ClientTokenService {

    /**
     * Get admin token
     *
     * @param login
     * @return Admin Token as String
     * @see Login
     */
    @POST("V1/integration/admin/token")
    Observable<String> getAccessAdminToken(@Body Login login);

    /**
     * Get customer access token
     *
     * @param login
     * @return Access token of customer as String
     * @see Login
     */
    @POST("V1/integration/customer/token")
    Observable<String> getAccessCustomerToken(@Body Login login);

    /**
     * Get customer access token
     * @param socialLoginForm SocialLoginForm
     * @return
     */
    @POST("V2/eshopping/customer/socialLogin")
    Observable<String> getAccessCustomerTokenWithSocialAccount(@Body SocialLoginForm socialLoginForm);
    
}
