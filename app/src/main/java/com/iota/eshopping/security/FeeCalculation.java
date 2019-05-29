package com.iota.eshopping.security;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by channarith.bong on 3/19/18.
 *
 * @author channarith.bong
 */
public class FeeCalculation {

    /**
     * If distance < 6km
     * => Delivery fee = 1$
     * If distance => 6km and distance <= 8km
     * => Delivery fee = 2$
     * If distance > 8km and distance <= 11km
     * => Delivery fee = 3$
     * And if distance > 11 km the shop will not be displayed in the list of shops
     */
    private static final float FEE_LEVEL_I = 1;
    private static final float FEE_LEVEL_II = 2;
    private static final float FEE_LEVEL_III = 3;

    /**
     * @param source LatLng
     * @param destination LatLng
     * @return Float
     */
    public static Float getFeeByDistant(LatLng source, LatLng destination) {
        double distant = meterDistanceBetweenPoints(source, destination);
        if (distant < 6) {
            return FEE_LEVEL_I;
        } else if (distant >= 6 && distant <= 8) {
            return FEE_LEVEL_II;
        } else if (distant > 8 && distant <= 11) {
            return FEE_LEVEL_III;
        }
        return 0f;
    }

    /**
     * @param latLngA LatLng
     * @param latLngB LatLng
     * @return double
     */
    public static double meterDistanceBetweenPoints(LatLng latLngA, LatLng latLngB) {
        return meterDistanceBetweenPoints(latLngA.latitude, latLngA.longitude, latLngB.latitude, latLngB.longitude);
    }

    /**
     * @param latA LatLng
     * @param lngA LatLng
     * @param latB LatLng
     * @param lngB LatLng
     * @return double
     */
    private static double meterDistanceBetweenPoints(double latA, double lngA, double latB, double lngB) {
        float[] distance = new float[2];
        Location.distanceBetween(latA, lngA, latB, lngB, distance);
        return distance[0] / 1000;
    }

}
