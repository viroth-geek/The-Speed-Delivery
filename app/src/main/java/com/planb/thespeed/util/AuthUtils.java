package com.planb.thespeed.util;

import android.util.Log;

import com.planb.thespeed.model.Customer;
import com.planb.thespeed.service.datahelper.datasource.online.FetchCustomer;

/**
 * @author yeakleang.ay on 10/18/18.
 */

public class AuthUtils {

    /**
     * isTokenValid
     * @param token String
     * @param authValid AuthValid
     */
    public static void isTokenValid(String token, AuthValid authValid) {
        getUserDetails(token, customer -> {
            authValid.isAuthValid(customer != null);
        });
    }

    /**
     *
     * @param token String
     * @param authCallBack AuthCallBack
     */
    private static void getUserDetails(String token, AuthCallBack authCallBack) {
        new FetchCustomer(token, new FetchCustomer.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(Customer customerInfo) {
                authCallBack.validate(customerInfo);
            }

            @Override
            public void onError(Throwable e) {
                authCallBack.validate(null);
            }
        });
    }

    /**
     *
     */
    public interface AuthCallBack {
        void validate(Customer customer);
    }

    /**
     *
     */
    public interface AuthValid {
        void isAuthValid(boolean isValid);
    }
}