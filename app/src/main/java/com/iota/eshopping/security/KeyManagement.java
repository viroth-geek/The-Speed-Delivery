package com.iota.eshopping.security;

import com.iota.eshopping.constant.ApplicationConfiguration;

/**
 * Created by channarith.bong on 12/21/17.
 *
 * @author channarith.bong
 */

public class KeyManagement {

    private String tokenCustomer;
    private String tokenAdmin;
    private String postmanApi;
    private String quoteId;

    private static KeyManagement instance;

    /**
     *
     */
    private KeyManagement() {
        // Development Key
        setTokenAdmin(ApplicationConfiguration.ADMIN_TOKEN);
    }

    /**
     * @return KeyManagement
     */
    public static KeyManagement getInstance() {
        if (instance == null) {
            instance = new KeyManagement();
        }
        return instance;
    }

    /**
     * @return String
     */
    public String getTokenCustomer() {
        return tokenCustomer;
    }

    /**
     * @param tokenCustomer String
     */
    void setTokenCustomer(String tokenCustomer) {
        this.tokenCustomer = tokenCustomer;
    }

    /**
     * @return String
     */
    public String getPostmanApi() {
        return postmanApi;
    }

    /**
     * @param postmanApi String
     */
    public void setPostmanApi(String postmanApi) {
        this.postmanApi = postmanApi;
    }

    /**
     * @param instance KeyManagement
     */
    public static void setInstance(KeyManagement instance) {
        KeyManagement.instance = instance;
    }

    /**
     * @return String
     */
    public String getQuoteId() {
        return quoteId;
    }

    /**
     * @param quoteId String
     */
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    /**
     * @return String
     */
    public String getTokenAdmin() {
        return tokenAdmin;
    }

    /**
     * @param tokenAdmin String
     */
    private void setTokenAdmin(String tokenAdmin) {
        this.tokenAdmin = tokenAdmin;
    }
}
