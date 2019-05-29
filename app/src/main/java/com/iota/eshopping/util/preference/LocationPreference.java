package com.iota.eshopping.util.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.iota.eshopping.model.Address;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author yeakleang.ay
 */
public class LocationPreference {

    private static final String ADDRESS = "address";

    private static final String LOCATION_PREFERENCE = "LocationPreference";

    private static SharedPreferences preferences;

    /**
     * @param context Context
     * @param address Address
     */
    public static void saveLocation(Context context, Address address) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(LOCATION_PREFERENCE, Context.MODE_PRIVATE);
        }
        address.setStreet(new ArrayList<>(Collections.singletonList(address.getAddressLine())));
        final Gson gson = new Gson();
        String serializeObject = gson.toJson(address);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ADDRESS, serializeObject);
        editor.apply();
    }

    /**
     * @param context Context
     * @return Address
     */
    public static Address getLocation(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(LOCATION_PREFERENCE, Context.MODE_PRIVATE);
        }
        if (preferences.contains(ADDRESS)) {
            final Gson gson = new Gson();
            return gson.fromJson(preferences.getString(ADDRESS, ""), Address.class);
        }
        return null;
    }

    /**
     * @param context Context
     */
    public static void clear(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(LOCATION_PREFERENCE, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ADDRESS, "");
        editor.apply();
    }

}