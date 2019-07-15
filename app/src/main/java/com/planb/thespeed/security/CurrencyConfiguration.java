package com.planb.thespeed.security;

import java.text.DecimalFormat;

/**
 * @author channarith.bong
 */
public class CurrencyConfiguration {

    private static final String DOLLAR_US = "$";
    private static final String RIEL_KH = "៛";

    /**
     * @return String
     */
    public static String getDollarSign() {
        return DOLLAR_US;
    }

    /**
     * @return String
     */
    public static String getRielSign() {
        return RIEL_KH;
    }

    /**
     *
     * @param value double
     * @param rate Integer
     * @return String
     */
    public static String getRielValue(double value, Integer rate) {
        double priceInRiel = ((Math.round(value * rate) / 100) * 100) + (Math.round(value * rate)) % 100 / 50 * 100;
        return RIEL_KH + new DecimalFormat("#,###").format(priceInRiel);

    }

}
