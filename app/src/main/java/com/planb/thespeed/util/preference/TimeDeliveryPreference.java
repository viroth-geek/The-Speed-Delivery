package com.planb.thespeed.util.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author yeakleang.ay
 */
public class TimeDeliveryPreference {

    private static final String TIME_DELIVERY = "TimeDelivery";

    private static final String TIME_DELIVERY_TEXT = "TimeDeliveryText";

    private static final String TIME_DELIVERY_PREFERENCE = "TimeDeliveryPreference";

    private static SharedPreferences preferences;

    /**
     *
     * @param context Context
     * @param time String
     */
    public static void saveTimeDelivery(Context context, String time) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(TIME_DELIVERY_PREFERENCE, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TIME_DELIVERY, time);
        editor.apply();
    }

    public static void saveTimeDeliveryText(Context context, String time) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(TIME_DELIVERY_PREFERENCE, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TIME_DELIVERY_TEXT, time);
        editor.apply();
    }

    /**
     * @param context Context
     * @return String
     */
    public static String getTimeDelivery(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(TIME_DELIVERY_PREFERENCE, Context.MODE_PRIVATE);
        }
        if (preferences.contains(TIME_DELIVERY)) {
            return preferences.getString(TIME_DELIVERY, "");
        }
        return null;
    }

    /**
     * @param context Context
     * @return String
     */
    public static String getTimeDeliveryText(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(TIME_DELIVERY_PREFERENCE, Context.MODE_PRIVATE);
        }
        if (preferences.contains(TIME_DELIVERY_TEXT)) {
            return preferences.getString(TIME_DELIVERY_TEXT, "");
        }
        return null;
    }

    /**
     * @param context Context
     */
    public static void clear(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(TIME_DELIVERY_PREFERENCE, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TIME_DELIVERY, "");
        editor.putString(TIME_DELIVERY_TEXT, "");
        editor.apply();
    }

}
