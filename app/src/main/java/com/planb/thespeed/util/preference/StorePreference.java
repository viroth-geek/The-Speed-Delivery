package com.planb.thespeed.util.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author yeakleang.ay
 */
public class StorePreference {

    private static final String SERVICE_FEE = "service_fee";

    private static final String STORE_PREFERENCE = "STORE_PREFERENCE";

    private static SharedPreferences preferences;

    /**
     *
     * @param context Context
     * @param serviceFee Float
     */
    public static void saveServiceFee(Context context, Float serviceFee) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(STORE_PREFERENCE, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(SERVICE_FEE, serviceFee);
        editor.apply();
    }

    /**
     * @param context Context
     * @return Float
     */
    public static Float getServiceFee(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(STORE_PREFERENCE, Context.MODE_PRIVATE);
        }
        if (preferences.contains(SERVICE_FEE)) {
            return preferences.getFloat(SERVICE_FEE, 0);
        }
        return 0F;
    }

    /**
     * @param context Context
     */
    public static void clearServiceFee(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(STORE_PREFERENCE, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(SERVICE_FEE, 0);
        editor.apply();
    }

}
