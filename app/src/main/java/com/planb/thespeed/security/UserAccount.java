package com.planb.thespeed.security;

import android.content.Context;

import com.planb.thespeed.model.Customer;
import com.planb.thespeed.server.DatabaseHelper;
import com.planb.thespeed.service.datahelper.datasource.offine.customer.FetchCustomerDAO;

/**
 * Created by channarith.bong on 1/18/18.
 *
 * @author channarith.bong
 */

public class UserAccount {

    private FetchCustomerDAO dao;

    /**
     * @param context Context
     */
    public UserAccount(Context context) {
        dao = new FetchCustomerDAO(DatabaseHelper.getInstance(context).getDatabase());
    }

    /**
     * @param customerInfo Customer
     * @return boolean
     */
    public boolean insertCustomer(Customer customerInfo) {
        return dao.replace(customerInfo);
    }

    /**
     * Get current customer info
     *
     * @return User information
     */
    public Customer getCustomer() {
        if (dao.getLastCustomer() != null) {
            return dao.getLastCustomer();
        }
        return null;
    }

    /**
     * Get current customer token
     *
     * @return return user token if user is logged in. return null if not
     */
    public String getCustomerToken() {
        if (dao.getLastCustomer() != null) {
            return dao.getLastCustomer().getRpToken();
        }
        return null;
    }

    /**
     * Check if user is already Logged in
     *
     * @return <b>True<b/> if user is logged in.
     */
    public boolean checkIsReadyLogged() {
        return dao.getNumberOfAccount() != null && dao.getNumberOfAccount() != 0;
    }

    /**
     * All user in table will be remove
     */
    public void deleteCustomer() {
        dao.removeAllUser();
    }

    /**
     * Assign customer token into system configuration
     * Return <b>true</b> if token successful request from server.
     */
    public boolean assignToken(String token) {
        if (dao.updateToken(token)) {
            KeyManagement.getInstance().setTokenCustomer(token);
            return true;
        }
        return false;
    }
}
