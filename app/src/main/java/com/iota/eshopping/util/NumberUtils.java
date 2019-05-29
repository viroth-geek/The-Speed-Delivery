package com.iota.eshopping.util;

/**
 * Created by channarith.bong on 12/20/17.
 */

public class NumberUtils {

    /**
     * Format to string currency
     *
     * @param money
     * @return
     */
    public static String strMoney(Float money) {
//        return new DecimalFormat("##.##").format(money);
        return String.format("%.2f", money);
    }
}
