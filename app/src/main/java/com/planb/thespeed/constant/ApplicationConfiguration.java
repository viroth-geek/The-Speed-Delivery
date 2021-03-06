package com.planb.thespeed.constant;

/**
 * Created by channarith.bong on 12/21/17.
 * @author channarith.bong
 */

public class ApplicationConfiguration {

    public static final int DATABASE_VERSION = 5;

//    public static final String BASE_URL = "https://192.168.88.69:445/index.php/rest/";
//    public static final String PRODUCT_IMAGE_URL = "https://192.168.88.69:445/pub/media/catalog/product/";
//    public static final String STORE_IMAGE_URL = "https://192.168.88.69:445/pub/media/catalog/category/";
//    public static final String ADMIN_TOKEN = "jrymw1l1jmnr3bix920uqug0ityoeh2k";
//    public static final String CERT_NAME = "apache.pem";

//    public static final String BASE_URL = "https://eshopping-test.nokor-solutions.com:453/index.php/rest/";
//    public static final String PRODUCT_IMAGE_URL = "https://eshopping-test.nokor-solutions.com:453/pub/media/catalog/product/";
//    public static final String STORE_IMAGE_URL = "https://eshopping-test.nokor-solutions.com:453/pub/media/catalog/category/";
//    public static final String ADMIN_TOKEN = "mf50dv8lgttmllneuabg2qp4gfn9p2l5";
//    public static final String CERT_NAME = "eshopping_test.crt";

    public static final String BASE_URL = "https://www.thespeeddelivery.com/index.php/rest/";
    public static final String PRODUCT_IMAGE_URL = "https://www.thespeeddelivery.com/pub/media/catalog/product/";
    public static final String STORE_IMAGE_URL = "https://www.thespeeddelivery.com/pub/media/catalog/category/";
    public static final String ADMIN_TOKEN = "tuy3kac6tr4o33eorfs9xtkyqufrhvpo";
    public static final String CERT_NAME = "fullchain.pem";

    // Enable all 'Log.d()' when DEVELOPER_MODE = true
    public static final Boolean DEVELOPER_MODE = true; // PRODUCTION_MODE or DEVELOPER_MODE = false

    public static final int MAX_ITEM_ALLOW = 100;
    public static final int MAX_DISTANT_ALLOW = 11;

    public static final String PAGE_PRIVACY_POLICY = "privacy-policy";
    public static final String PAGE_TERMS_CONDITIONS = "terms-conditions";
    public static final String PAGE_ABOUT = "about-us";
    public static final String PAGE_SPECIAL_PROMOTION= "special-promotion";

    public static final int LIMIT = 20;
}